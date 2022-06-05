package ru.vezdecod.restback.responses;

public class VotesListResponse {
    private final String name;

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    private int votes;

    public VotesListResponse(String name, int votes) {
        this.name = name;
        this.votes = votes;
    }

    public void addVote() {
        this.votes ++;
    }
}
