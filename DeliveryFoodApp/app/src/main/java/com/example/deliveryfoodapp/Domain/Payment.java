package com.example.deliveryfoodapp.Domain;

public class Payment {
    String email, timeOrder, address, district, totalItems, deliveryFee, taxFee, totalBill;

    public Payment() {
    }

    public Payment(String email, String timeOrder, String address, String district, String totalItems, String deliveryFee, String taxFee, String totalBill) {
        this.email = email;
        this.timeOrder = timeOrder;
        this.address = address;
        this.district = district;
        this.totalItems = totalItems;
        this.deliveryFee = deliveryFee;
        this.taxFee = taxFee;
        this.totalBill = totalBill;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(String deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public String getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(String taxFee) {
        this.taxFee = taxFee;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(String totalBill) {
        this.totalBill = totalBill;
    }
}
