package ru.vezdecod.restback.entity;

import io.github.bucket4j.Bucket;

public class VoteBucketCollection {
    private Vote vote;

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public void setBucket(Bucket bucket) {
        this.bucket = bucket;
    }

    public VoteBucketCollection(Vote vote, Bucket bucket) {
        this.vote = vote;
        this.bucket = bucket;
    }

    public Vote getVote() {
        return vote;
    }

    public Bucket getBucket() {
        return bucket;
    }

    private Bucket bucket;
}
