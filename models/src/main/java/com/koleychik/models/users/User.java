package com.koleychik.models.users;

import android.net.Uri;

import java.util.List;

public class User {

    private String id, name, email;
    private List<Long> listDialogsIds;
    private Uri icon, background;
    private boolean isOnline = false;
    private long created;

    public User(String id, String name, String email, List<Long> listDialogsIds, Uri icon, Uri background, boolean isOnline, long created) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.listDialogsIds = listDialogsIds;
        this.icon = icon;
        this.background = background;
        this.isOnline = isOnline;
        this.created = created;
    }

    public User(String id, String name, String email, List<Long> listDialogsIds) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.listDialogsIds = listDialogsIds;
    }

    public User(String id, String name, String email, List<Long> listDialogsIds, Uri icon) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.listDialogsIds = listDialogsIds;
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

    public List<Long> getListDialogsIds() {
        return listDialogsIds;
    }

    public void setListDialogsIds(List<Long> listDialogsIds) {
        this.listDialogsIds = listDialogsIds;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}