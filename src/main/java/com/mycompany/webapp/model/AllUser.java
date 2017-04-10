package com.mycompany.webapp.model;

import java.util.ArrayList;
import java.util.List;


public class AllUser {
    private int id;
    private String username;
    private String password;
    private List<String> roles = new ArrayList<>();
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<String> getRoles() {

        return roles;

    }

    public void addRole(String role) {

        this.roles.add(role);

    }

    public boolean hasRole(String role) {

        return this.roles.contains(role);

    }

}
