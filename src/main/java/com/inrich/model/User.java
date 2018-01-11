package com.inrich.model;

/**
 * Created by nowcoder on 2016/6/26.
 */
public class User {
    private int id;
    private String name;
    private String password;

    public User() {

    }
    public User(String name) {
        this.name = name;
        this.password = "";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
