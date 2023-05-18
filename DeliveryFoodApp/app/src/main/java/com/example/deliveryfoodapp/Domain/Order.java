package com.example.deliveryfoodapp.Domain;

public class Order {
    public String fullname, phone, email, address, district, message, totalItems, deliveryFee, taxFee, totalBill, timeOrder;

    public Order() {
    }

    public Order(String fullname, String email, String phone, String address, String district, String message, String totalItems, String deliveryFee, String taxFee, String totalBill, String timeOrder) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.district = district;
        this.message = message;
        this.totalItems = totalItems;
        this.deliveryFee = deliveryFee;
        this.taxFee = taxFee;
        this.totalBill = totalBill;
        this.timeOrder = timeOrder;
    }

    public Order(String fullname, String email, String phone, String address, String district, String totalItems, String deliveryFee, String taxFee, String totalBill, String timeOrder) {
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.district = district;
        this.totalItems = totalItems;
        this.deliveryFee = deliveryFee;
        this.taxFee = taxFee;
        this.totalBill = totalBill;
        this.timeOrder = timeOrder;
    }


    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }
}
