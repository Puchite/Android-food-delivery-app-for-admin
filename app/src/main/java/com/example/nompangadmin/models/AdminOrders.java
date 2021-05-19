package com.example.nompangadmin.models;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminOrders  {

    public String name,phone,location,amount,receive;
    public  AdminOrders(){};

    public AdminOrders(String name, String phone, String location, String amount, String pickup) {
        this.name = name;
        this.phone = phone;
        this.location = location;
        this.amount = amount;
        this.receive = pickup;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAmount() {
        return amount;
    }

    public String getReceive() {
        return receive;
    }

    public void setReceive(String pickup) {
        this.receive = pickup;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
