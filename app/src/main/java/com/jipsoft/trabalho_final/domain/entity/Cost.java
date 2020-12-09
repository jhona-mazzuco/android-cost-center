package com.jipsoft.trabalho_final.domain.entity;

public class Cost extends BaseEntity {

    private String name;

    private Double price;

    private Center center;

    public Cost(int id, String name, Double price, Center center) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.center = center;
    }

    public Cost(String name, Double price, Center center) {
        this.name = name;
        this.price = price;
        this.center = center;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Center getCenter() {
        return center;
    }

    public void setCenter(Center center) {
        this.center = center;
    }
}
