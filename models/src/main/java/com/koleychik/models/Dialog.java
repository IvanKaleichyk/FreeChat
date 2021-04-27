package com.koleychik.models;

import com.koleychik.models.users.User;

public class Dialog {

    private long id;
    private User user1, user2;
    private Message lastMessage;
    private boolean isNewMessages, isFavorite;

    public Dialog(long id, User user1, User user2, Message lastMessage, boolean isNewMessages, boolean isFavorite) {
        this.id = id;
        this.user1 = user1;
        this.user2 = user2;
        this.lastMessage = lastMessage;
        this.isNewMessages = isNewMessages;
        this.isFavorite = isFavorite;
    }

    public Dialog() {
    }

    public long getId() {
        return id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setNameUser2(User user2) {
        this.user2 = user2;
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
