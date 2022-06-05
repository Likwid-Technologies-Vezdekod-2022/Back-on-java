package ru.vezdecod.restback.requests;

import ru.vezdecod.restback.DB.LegacyInMemoryDataBase;

import java.util.List;
import java.util.Objects;

public class VoteListRequest {
    private long from;
    private long to;
    private int intervals;
    private List<String> artists;

    public void setFrom(int from) {
        this.from = from;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public void setIntervals(int intervals) {
        this.intervals = intervals;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    public int getIntervals() {
        return intervals;
    }

    public List<String> getArtists() {
        return artists;
    }

    public VoteListRequest(long from, long to, int intervals, String artists) {
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        this.to = to;
        this.from = from;
        this.intervals = intervals;
        this.artists = Objects.equals(artists, "") ? dataBase.getArtists(): List.of(artists.split(","));
    }
}
