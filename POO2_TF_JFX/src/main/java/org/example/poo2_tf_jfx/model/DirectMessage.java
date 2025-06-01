package org.example.poo2_tf_jfx.model;

import java.time.LocalDateTime;

public class DirectMessage {
    private int id;
    private User userSender;
    private User userReceiver;
    private String message;
    private LocalDateTime sendingDate;

    public DirectMessage(int id, User userSender, User userReceiver, String message, LocalDateTime sendingDate) {
        this.id = id;
        this.userSender = userSender;
        this.userReceiver = userReceiver;
        this.message = message;
        this.sendingDate = LocalDateTime.now();

    }

    private boolean userIsSender(User user){
        return this.userSender.equals(user);
    }

    private boolean userIsReceiver(User user){
        return userReceiver.equals(user);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUserSender() {
        return userSender;
    }

    public void setUserSender(User userSender) {
        this.userSender = userSender;
    }

    public User getUserReceiver() {
        return userReceiver;
    }

    public void setUserReceiver(User userReceiver) {
        this.userReceiver = userReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getSendingDate() {
        return sendingDate;
    }
}