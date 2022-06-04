package ru.vezdecod.restback.entity;

public class Vote {
    private String phone;
    private String artist;

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
    }

    @Override
    public String toString() {
        return phone + " " + artist;
    }
}
