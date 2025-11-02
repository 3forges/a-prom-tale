package com.example;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Fruit implements Serializable {

    private String name;
    private String price;

    public String getPrice() {
        return this.price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    @Override
    public String toString() {
        return "Fruit{" +
                "\"name\"=\"" + this.name + "\"," + 
                "\"price\"=\"" + this.price + "\"," +
                '}';
    }
}
