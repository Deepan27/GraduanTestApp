package com.deepcode.graduantestapp.model;

import com.google.gson.annotations.SerializedName;

public class FormData {
    private String email;
    private String password;

    private String title;

    @SerializedName("name")
    private String name;

    public FormData(String name) {
        this.name = name;
    }

    public FormData(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
