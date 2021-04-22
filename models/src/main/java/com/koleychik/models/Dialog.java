package com.koleychik.models;

public class Dialog {

    private long id;
    private String nameUser1, nameUser2;
    private Message lastMessage;
    private boolean isNewMessages, isFavorite;

    public Dialog(long id, String nameUser1, String nameUser2, Message lastMessage, boolean isNewMessages, boolean isFavorite) {
        this.id = id;
        this.nameUser1 = nameUser1;
        this.nameUser2 = nameUser2;
        this.lastMessage = lastMessage;
        this.isNewMessages = isNewMessages;
        this.isFavorite = isFavorite;
    }

    public Dialog() {
    }

    public long getId() {
        return id;
    }

    public String getNameUser1() {
        return nameUser1;
    }

    public void setNameUser1(String nameUser1) {
        this.nameUser1 = nameUser1;
    }

    public String getNameUser2() {
        return nameUser2;
    }

    public void setNameUser2(String nameUser2) {
        this.nameUser2 = nameUser2;
    }

    public Message getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(Message lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean isNewMessages() {
        return isNewMessages;
    }

    public void setNewMessages(boolean newMessages) {
        isNewMessages = newMessages;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }
}
