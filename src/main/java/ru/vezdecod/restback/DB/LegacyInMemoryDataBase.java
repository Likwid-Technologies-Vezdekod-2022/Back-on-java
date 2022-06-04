package ru.vezdecod.restback.DB;

import ru.vezdecod.restback.entity.Vote;

import java.util.ArrayList;
import java.util.List;

public class LegacyInMemoryDataBase {
    private static final LegacyInMemoryDataBase instance = new LegacyInMemoryDataBase();
    private List<Vote> votes;
    private List<String> artists;

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void addArtist(String artist) {
        if (artists == null) {
            artists = new ArrayList<>();
        }
        artists.add(artist);
    }

    public void addVote(Vote vote) {
        if (votes == null) {
            votes = new ArrayList<>();
        }
        votes.add(vote);
    }
    public List<Vote> getVotes() {
        return votes;
    }


    private LegacyInMemoryDataBase() {
    }

    public static LegacyInMemoryDataBase getInstance() {
        return instance;
    }
}
