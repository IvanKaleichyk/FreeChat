package com.koleychik.models;

import android.net.Uri;

public class User {

    private String id, name, email;
    private Uri icon, background;

    public User(String id, String name, String email, Uri icon, Uri background) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.icon = icon;
        this.background = background;
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public User(String id, String name, String email, Uri icon) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.icon = icon;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Uri getIcon() {
        return icon;
    }

    public void setIcon(Uri icon) {
        this.icon = icon;
    }

    public Uri getBackground() {
        return background;
    }

    public void setBackground(Uri background) {
        this.background = background;
    }
}