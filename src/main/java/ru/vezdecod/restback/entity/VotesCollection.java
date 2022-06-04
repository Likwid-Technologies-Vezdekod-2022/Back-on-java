package ru.vezdecod.restback.entity;

public class VotesCollection {
    private final String name;

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    private int votes;

    public VotesCollection(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    public void addVote() {
        this.votes ++;
    }
}
