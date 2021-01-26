package com.example.nompangadmin.models;

public class models {
    private String product,type,drinkName,disc,name,sweet;
    models(){

    }
    public  models(String product,String type,String drinkName,String dis,String name,String sweet)
    {
        this.product = product;
        this.type = type;
        this.drinkName = drinkName;
        this.disc = disc;
        this.name = name;
        this.sweet = sweet;
    }

    public String getProduct() {
        return product;
    }

    public String getType() {
        return type;
    }

    public String getDrinkName() {
        return drinkName;
    }

    public String getDisc() {
        return disc;
    }

    public String getName() {
        return name;
    }

    public String getSweet() {
        return sweet;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDrinkName(String drinkName) {
        this.drinkName = drinkName;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSweet(String sweet) {
        this.sweet = sweet;
    }
}
