package org.example.poo2_tf_jfx.model;

public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private Profile profile;

    public User(int id, String name, String email, String password, Profile profile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profile = profile;
    }

    public boolean authenticate(String inputEmail, String inputPassword) {
        return this.email.equals(inputEmail) && this.password.equals(inputPassword);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
