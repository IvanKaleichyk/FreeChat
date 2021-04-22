package com.koleychik.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Message {

    @PrimaryKey(autoGenerate = true)
    private long id, dialogId;
    private String text, authorUid, topic;
    private boolean isRead;
    private long dateCreated;

    public Message(long id, int dialogId, String text, String authorUid, String topic, boolean isRead, long dateCreated) {
        this.id = id;
        this.dialogId = dialogId;
        this.text = text;
        this.authorUid = authorUid;
        this.topic = topic;
        this.isRead = isRead;
        this.dateCreated = dateCreated;
    }

    public Message() {
    }

    public long getId() {
        return id;
    }

    public long getDialogId() {
        return dialogId;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthorUid() {
        return authorUid;
    }

    public void setAuthorUid(String authorUid) {
        this.authorUid = authorUid;
    }

    public long getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(long dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
