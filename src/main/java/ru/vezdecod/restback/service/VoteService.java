package ru.vezdecod.restback.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vezdecod.restback.DB.LegacyInMemoryDataBase;
import ru.vezdecod.restback.entity.Vote;
import ru.vezdecod.restback.entity.VoteBucketCollection;
import ru.vezdecod.restback.entity.VotesCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class VoteService {
    public List<VotesCollection> list() {
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        List<VotesCollection> data = new ArrayList<VotesCollection>();
        List<String> artists = dataBase.getArtists();
        List<Vote> votes = dataBase.getVotes();
        if (votes == null) {
            votes = new ArrayList<Vote>();
        }
        for (String s : artists) {
            data.add(new VotesCollection(s, 0));
        }
        for (Vote vote : votes) {
            String artist = vote.getArtist();
            List<VotesCollection> result = data.stream().filter(item -> item.getName().equals(artist)).toList();
            result.get(0).addVote();
        }
        return data;
    }

    public ResponseEntity add(Vote vote) {
        if (!Pattern.matches("9\\d{9}", vote.getPhone())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        List<VoteBucketCollection> collections = dataBase.getVoteBucketCollection();
        List<VoteBucketCollection> result = collections.stream().filter(item -> item.getVote().getPhone().equals(vote.getPhone())).toList();
        VoteBucketCollection collection;
        if (result.size() == 0) {
            collection = dataBase.addVoteBucketCollection(vote);
        }
        else {
            collection = result.get(0);
        }
        if (!collection.getBucket().tryConsume(1)){
            return new ResponseEntity(HttpStatus.TOO_MANY_REQUESTS);
        }

        if (vote.getArtist() == null || vote.getPhone() == null ) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        try {
            if (dataBase.getArtists().contains(vote.getArtist())) {
                dataBase.addVote(vote);
                return new ResponseEntity(HttpStatus.CREATED);
            }
        }
        catch (NullPointerException e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }
}
