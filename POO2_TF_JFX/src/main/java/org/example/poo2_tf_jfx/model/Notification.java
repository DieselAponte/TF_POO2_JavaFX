package org.example.poo2_tf_jfx.model;

import java.time.LocalDate;

public class Notification {
    private String titleMessage;
    private String textMessage;
    private LocalDate sendingDate;
    private boolean read;

    public Notification(String titleMessage, String textMessage) {
        this.titleMessage = titleMessage;
        this.textMessage = textMessage;
        this.sendingDate = LocalDate.now();
        this.read = false;
    }

    public boolean messageIsRead() {
        return read;
    }

    public void markAsRead() {
        this.read = true;
    }

    public String getTitleMessage() {
        return titleMessage;
    }

    public void setTitleMessage(String titleMessage) {
        this.titleMessage = titleMessage;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
