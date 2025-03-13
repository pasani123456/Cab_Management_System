package com.megacitycab.model;

public class Customer {
    private String regNumber;
    private String name;
    private String address;
    private String nic;
    private String telephone;
    
    
    public Customer(){
        
    }
    
    public Customer(String regNumber, String name, String address, String nic, String telephone) {
        this.regNumber = regNumber;
        this.name = name;
        this.address = address;
        this.nic = nic;
        this.telephone = telephone;
    }
    
    // Getters and Setters
    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    
    @Override
    public String toString() {
        return regNumber + "," + name + "," + address + "," + nic + "," + telephone;
    }
}