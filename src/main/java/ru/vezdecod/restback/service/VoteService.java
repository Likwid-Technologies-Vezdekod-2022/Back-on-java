package ru.vezdecod.restback.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vezdecod.restback.DB.LegacyInMemoryDataBase;
import ru.vezdecod.restback.entity.Vote;
import ru.vezdecod.restback.entity.VotesCollection;

import java.util.ArrayList;
import java.util.List;

@Service
public class VoteService {
    public List<VotesCollection> list() {
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
//        Map<String,Integer> ar = new HashMap<String,Integer>();
        List<VotesCollection> data = new ArrayList<VotesCollection>();
        List<String> artists = dataBase.getArtists();
        List<Vote> votes = dataBase.getVotes();
        if (votes == null) {
            votes = new ArrayList<Vote>();
        }
        for (int i = 0; i < artists.size(); i++) {
            data.add(new VotesCollection(artists.get(i), 0));
        }
        for (int i = 0; i < votes.size(); i++) {
            String artist = votes.get(i).getArtist();
            List<VotesCollection> result = data.stream().filter(item -> item.getName().equals(artist)).toList();
            result.get(0).addVote();

        }
        return data;
    }

    public ResponseEntity add(Vote vote) {
        if (vote.getArtist() == null || vote.getPhone() == null ) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
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
