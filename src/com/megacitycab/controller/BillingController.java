package com.megacitycab.controller;

import com.megacitycab.model.Bill;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BillingController {

    private static final String BILLS_FILE = "data/bills.txt";

    public boolean createBill(Bill bill) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BILLS_FILE, true))) {
            writer.write(bill.toString());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Bill> getAllBills() {
        List<Bill> bills = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(BILLS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Bill bill = new Bill(
                    parts[0], parts[1], LocalDateTime.parse(parts[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                    Double.parseDouble(parts[3]), Double.parseDouble(parts[4]), Double.parseDouble(parts[5])
                );
                bill.setPaymentStatus(parts[7]);
                bills.add(bill);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bills;
    }

    public Bill getBillByNumber(String billNumber) {
        return getAllBills().stream()
                .filter(bill -> bill.getBillNumber().equals(billNumber))
                .findFirst()
                .orElse(null);
    }

    public boolean updateBill(Bill updatedBill) {
        List<Bill> bills = getAllBills();
        for (int i = 0; i < bills.size(); i++) {
            if (bills.get(i).getBillNumber().equals(updatedBill.getBillNumber())) {
                bills.set(i, updatedBill);
                return saveAllBills(bills);
            }
        }
        return false;
    }

    private boolean saveAllBills(List<Bill> bills) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BILLS_FILE))) {
            for (Bill bill : bills) {
                writer.write(bill.toString());
                writer.newLine();
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}