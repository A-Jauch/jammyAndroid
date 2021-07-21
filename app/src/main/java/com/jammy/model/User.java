package com.jammy.model;

import java.util.Date;

public class User {
    private int id;
    private String email;
    private String password;
    private String name;
    private String lastname;
    private Date birthday;
    private Role role;
    private Instrument instrument;
    private int instrument_id;
    private String fcm_token;

    public User() {
    }

    public User(int id, String email, String password, String name, String lastname, Date birthday, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.role = role;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String name, String lastname, Date birthday,int instrument_id) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.instrument_id = instrument_id;
    }

    public User(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public User(int id, String email, String password, String name, String lastname, Date birthday, Role role, Instrument instrument, int instrument_id, String fcm_token) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastname = lastname;
        this.birthday = birthday;
        this.role = role;
        this.instrument = instrument;
        this.instrument_id = instrument_id;
        this.fcm_token = fcm_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Instrument getInstrument() {
        return instrument;
    }


    public int getInstrument_id() {
        return instrument_id;
    }

    public void setInstrument_id(int instrument_id) {
        this.instrument_id = instrument_id;
    }

    public String getFcm_token() {
        return fcm_token;
    }

    public void setFcm_token(String fcm_token) {
        this.fcm_token = fcm_token;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
