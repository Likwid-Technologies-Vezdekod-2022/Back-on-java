package ru.vezdecod.restback.entity;

import java.util.Date;

public class Vote {
    private String phone;
    private String artist;

    private final long saveDate;

    public long getSaveDate() {
        return saveDate;
    }

    public String getPhone() {
        return phone;
    }

    public String getArtist() {
        return artist;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Vote(String phone, String artist) {
        this.phone = phone;
        this.artist = artist;
        this.saveDate = new Date().getTime();
    }

    @Override
    public String toString() {
        return phone + " " + artist;
    }
}
