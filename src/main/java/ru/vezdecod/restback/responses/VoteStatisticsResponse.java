package ru.vezdecod.restback.responses;

public class VoteStatisticsResponse {
    private long start;
    private long end;
    private long votes;

    public void setStart(long start) {
        this.start = start;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public void setVotes(long votes) {
        this.votes = votes;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public long getVotes() {
        return votes;
    }

    public VoteStatisticsResponse(long start, long end, long votes) {
        this.start = start;
        this.end = end;
        this.votes = votes;
    }

    public void addVote() {
        votes++;
    }
}
