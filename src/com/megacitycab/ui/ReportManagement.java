package com.megacitycab.ui;

import com.megacitycab.controller.BookingController;
import com.megacitycab.controller.BillingController;
import com.megacitycab.model.Booking;
import com.megacitycab.model.Bill;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

// For Charts
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class ReportManagement extends JFrame {

    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel revenuePanel;
    private JComboBox<String> cmbRevenueReportType;
    private JTable tblRevenue;
    private DefaultTableModel revenueTableModel;
    private JButton btnGenerateRevenueReport;
    private JButton btnExportRevenueReport;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private BookingController bookingController;
    private BillingController billController;
    private List<Booking> allBookings;
    private List<Bill> allBills;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public ReportManagement() {
        bookingController = new BookingController();
        billController = new BillingController();
        // Load data
        allBookings = bookingController.getAllBookings();
        allBills = billController.getAllBills();
        initComponents();
        setTitle("Report Management");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    private void initComponents() {
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane = new JTabbedPane();

        // Initialize tabs
        initRevenuePanel();
        tabbedPane.addTab("Revenue Reports", revenuePanel);

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    private void initRevenuePanel() {
        revenuePanel = new JPanel(new BorderLayout(10, 10));
        revenuePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Control Panel
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Report Type
        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(new JLabel("Report Type:"), gbc);
        cmbRevenueReportType = new JComboBox<>(new String[]{
            "Daily Revenue", "Monthly Revenue"
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        controlPanel.add(cmbRevenueReportType, gbc);

        // Start Date
        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(new JLabel("Start Date (yyyy-MM-dd):"), gbc);
        txtStartDate = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        controlPanel.add(txtStartDate, gbc);

        // End Date
        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("End Date (yyyy-MM-dd):"), gbc);
        txtEndDate = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        controlPanel.add(txtEndDate, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnGenerateRevenueReport = createStyledButton("Generate Report", new Color(0, 128, 0), Color.WHITE);
        btnGenerateRevenueReport.addActionListener((ActionEvent e) -> generateRevenueReport());
        buttonPanel.add(btnGenerateRevenueReport);

        btnExportRevenueReport = createStyledButton("Export to CSV", new Color(0, 0, 128), Color.WHITE);
        btnExportRevenueReport.addActionListener((ActionEvent e) -> exportRevenueReport());
        buttonPanel.add(btnExportRevenueReport);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        controlPanel.add(buttonPanel, gbc);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Revenue Data"));

        String[] revenueColumns = {"Date/Period", "Total Revenue", "Number of Bookings", "Average Revenue"};
        revenueTableModel = new DefaultTableModel(revenueColumns, 0);
        tblRevenue = new JTable(revenueTableModel);
        JScrollPane scrollPane = new JScrollPane(tblRevenue);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Chart Panel
        JPanel chartPanel = new JPanel(new BorderLayout());
        chartPanel.setBorder(BorderFactory.createTitledBorder("Revenue Chart"));
        chartPanel.add(new JLabel("No chart data available"), BorderLayout.CENTER); // Placeholder

        // Add components to revenue panel
        revenuePanel.add(controlPanel, BorderLayout.NORTH);
        revenuePanel.add(tablePanel, BorderLayout.CENTER);
        revenuePanel.add(chartPanel, BorderLayout.SOUTH);
    }

   private void generateRevenueReport() {
    // Clear previous data
    revenueTableModel.setRowCount(0);

    // Access the SOUTH region of the BorderLayout
    JPanel chartPanel = (JPanel) revenuePanel.getComponent(2); // Assuming SOUTH is the third component
    chartPanel.removeAll(); // Clear existing chart

    // Get date range
    LocalDate startDate = parseDate(txtStartDate.getText());
    LocalDate endDate = parseDate(txtEndDate.getText());

    if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
        JOptionPane.showMessageDialog(this,
            "Please enter a valid date range (yyyy-MM-dd)",
            "Invalid Date Range",
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Filter bills by date range
    List<Bill> filteredBills = filterBillsByDateRange(startDate, endDate);

    // Generate report based on selected type
    String reportType = (String) cmbRevenueReportType.getSelectedItem();
    switch (reportType) {
        case "Daily Revenue":
            generateDailyRevenueReport(filteredBills, startDate, endDate);
            break;
        case "Monthly Revenue":
            generateMonthlyRevenueReport(filteredBills, startDate, endDate);
            break;
    }
}

    private void generateDailyRevenueReport(List<Bill> bills, LocalDate startDate, LocalDate endDate) {
        Map<LocalDate, Double> dailyRevenue = new HashMap<>();
        Map<LocalDate, Integer> dailyBookingCount = new HashMap<>();

        // Initialize map with all dates in range
        LocalDate date = startDate;
        while (!date.isAfter(endDate)) {
            dailyRevenue.put(date, 0.0);
            dailyBookingCount.put(date, 0);
            date = date.plusDays(1);
        }

        // Calculate revenue for each day
        for (Bill bill : bills) {
            LocalDate billDate = bill.getBillingDate().toLocalDate();
            if (!billDate.isBefore(startDate) && !billDate.isAfter(endDate)) {
                dailyRevenue.put(billDate, dailyRevenue.get(billDate) + bill.getTotalAmount());
                dailyBookingCount.put(billDate, dailyBookingCount.get(billDate) + 1);
            }
        }

        // Add data to table
        date = startDate;
        while (!date.isAfter(endDate)) {
            double revenue = dailyRevenue.get(date);
            int count = dailyBookingCount.get(date);
            double average = count > 0 ? revenue / count : 0;
            Object[] row = {
                date.format(dateFormatter),
                String.format("₹%.2f", revenue),
                count,
                String.format("₹%.2f", average)
            };
            revenueTableModel.addRow(row);
            date = date.plusDays(1);
        }

        // Add a chart for daily revenue
        addRevenueChart(dailyRevenue, "Daily Revenue");
    }

    private void generateMonthlyRevenueReport(List<Bill> bills, LocalDate startDate, LocalDate endDate) {
        Map<String, Double> monthlyRevenue = new HashMap<>();
        Map<String, Integer> monthlyBookingCount = new HashMap<>();

        for (Bill bill : bills) {
            LocalDate billDate = bill.getBillingDate().toLocalDate();
            if (!billDate.isBefore(startDate) && !billDate.isAfter(endDate)) {
                String monthYear = billDate.getYear() + "-" + String.format("%02d", billDate.getMonthValue());
                monthlyRevenue.put(monthYear, monthlyRevenue.getOrDefault(monthYear, 0.0) + bill.getTotalAmount());
                monthlyBookingCount.put(monthYear, monthlyBookingCount.getOrDefault(monthYear, 0) + 1);
            }
        }

        // Add data to table
        for (String monthYear : monthlyRevenue.keySet()) {
            double revenue = monthlyRevenue.get(monthYear);
            int count = monthlyBookingCount.get(monthYear);
            double average = count > 0 ? revenue / count : 0;
            Object[] row = {
                monthYear,
                String.format("₹%.2f", revenue),
                count,
                String.format("₹%.2f", average)
            };
            revenueTableModel.addRow(row);
        }

        // Add a chart for monthly revenue
        addRevenueChart(monthlyRevenue, "Monthly Revenue");
    }

  private void addRevenueChart(Map<?, Double> revenueData, String title) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();
    for (Map.Entry<?, Double> entry : revenueData.entrySet()) {
        dataset.addValue(entry.getValue(), "Revenue", entry.getKey().toString());
    }

    JFreeChart chart = ChartFactory.createBarChart(
        title, // Chart title
        "Date/Period", // Category axis label
        "Revenue (₹)", // Value axis label
        dataset, // Data
        PlotOrientation.VERTICAL,
        true, // Include legend
        true,
        false
    );

    // Create a new chart panel
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(800, 300)); // Fixed size for better layout

    // Replace the SOUTH region with the new chart panel
    JPanel chartContainer = new JPanel(new BorderLayout());
    chartContainer.add(chartPanel, BorderLayout.CENTER);

    revenuePanel.add(chartContainer, BorderLayout.SOUTH);
    revenuePanel.revalidate();
    revenuePanel.repaint();
}

    private void exportRevenueReport() {
        try (FileWriter writer = new FileWriter("revenue_report.csv")) {
            // Write header
            for (int i = 0; i < revenueTableModel.getColumnCount(); i++) {
                writer.append(revenueTableModel.getColumnName(i));
                if (i < revenueTableModel.getColumnCount() - 1) {
                    writer.append(",");
                }
            }
            writer.append("\n");

            // Write data
            for (int i = 0; i < revenueTableModel.getRowCount(); i++) {
                for (int j = 0; j < revenueTableModel.getColumnCount(); j++) {
                    writer.append(revenueTableModel.getValueAt(i, j).toString());
                    if (j < revenueTableModel.getColumnCount() - 1) {
                        writer.append(",");
                    }
                }
                writer.append("\n");
            }
            JOptionPane.showMessageDialog(this,
                "Report exported to revenue_report.csv",
                "Export Successful",
                JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Error exporting report: " + e.getMessage(),
                "Export Failed",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private LocalDate parseDate(String dateStr) {
        try {
            return LocalDate.parse(dateStr, dateFormatter);
        } catch (Exception e) {
            return null;
        }
    }

    private List<Bill> filterBillsByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Bill> filteredBills = new ArrayList<>();
        for (Bill bill : allBills) {
            LocalDate billDate = bill.getBillingDate().toLocalDate();
            if (!billDate.isBefore(startDate) && !billDate.isAfter(endDate)) {
                filteredBills.add(bill);
            }
        }
        return filteredBills;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ReportManagement().setVisible(true);
        });
    }
}