package ru.vezdecod.restback.DB;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import ru.vezdecod.restback.entity.Vote;
import ru.vezdecod.restback.entity.VoteBucketCollection;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class LegacyInMemoryDataBase {
    private static final LegacyInMemoryDataBase instance = new LegacyInMemoryDataBase();
    private List<Vote> votes;
    private List<String> artists;
    private int sendLimit;
    private List<VoteBucketCollection> voteBucketCollections;
    private long minWorkTime;
    private long maxWorkTime;
    private List<Long> timeList;

    private int requestsAmount;
    private int userAmount;

    public void setUserAmount(int userAmount) {
        this.userAmount = userAmount;
    }

    public int getUserAmount() {
        return userAmount;
    }

    public void setRequestsAmount(int requestsAmount) {
        this.requestsAmount = requestsAmount;
    }

    public int getRequestsAmount() {
        return requestsAmount;
    }

    public void addTimeList(long time){
        this.timeList.add(time);
    }

    public void setTimeList(List<Long> timeList) {
        this.timeList = timeList;
    }

    public List<Long> getTimeList() {
        return timeList;
    }

    public VoteBucketCollection addVoteBucketCollection(Vote vote) {
        Bucket bucket = Bucket4j.builder()
                .addLimit(Bandwidth.simple(sendLimit, Duration.ofMinutes(timeLimit)))
                .build();
        VoteBucketCollection voteBucketCollection = new VoteBucketCollection(vote,bucket);
        voteBucketCollections.add(voteBucketCollection);
        return voteBucketCollection;
    }

    public List<VoteBucketCollection> getVoteBucketCollection() {
        return voteBucketCollections;
    }
    public void setSendLimit(int sendLimit) {
        this.sendLimit = sendLimit;
    }

    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }

    private int timeLimit;

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    public void addArtist(String artist) {
        artists.add(artist);
    }

    public void addVote(Vote vote) {
        votes.add(vote);
    }
    public List<Vote> getVotes() {
        return votes;
    }


    private LegacyInMemoryDataBase() {
        votes = new ArrayList<>();
        artists = new ArrayList<>();
        voteBucketCollections = new ArrayList<>();
        timeList = new ArrayList<>();

    }

    public static LegacyInMemoryDataBase getInstance() {
        return instance;
    }
}
