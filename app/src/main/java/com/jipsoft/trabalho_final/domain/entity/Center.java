package com.jipsoft.trabalho_final.domain.entity;

public class Center {

    private int id;

    private String name;

    private User user;

    public Center(int id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Center(String name, User user) {
        this.name = name;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
