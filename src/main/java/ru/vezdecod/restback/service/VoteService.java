package ru.vezdecod.restback.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vezdecod.restback.DB.LegacyInMemoryDataBase;
import ru.vezdecod.restback.entity.*;
import ru.vezdecod.restback.requests.VoteListRequest;
import ru.vezdecod.restback.responses.VoteStatisticsResponse;
import ru.vezdecod.restback.responses.VotesListResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.random.RandomGenerator;
import java.util.regex.Pattern;

@Service
public class VoteService {
    public Map<String, List<VotesListResponse>> list() {
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        List<VotesListResponse> data = new ArrayList<>();
        List<Vote> votes = dataBase.getVotes();
        if (votes == null) {
            votes = new ArrayList<Vote>();
        }
        for (String s : dataBase.getArtists()) {
            data.add(new VotesListResponse(s, 0));
        }

        for (Vote vote : votes) {
            String artist = vote.getArtist();
            List<VotesListResponse> result = data.stream().filter(item -> item.getName().equals(artist)).toList();
            result.get(0).addVote();
        }
        Map<String, List<VotesListResponse>> data_map = Map.ofEntries(Map.entry("data", data));
        return data_map;
    }

    public Map<String, List<VoteStatisticsResponse>> statisticsResponses(VoteListRequest voteListRequest) {
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        List<VoteStatisticsResponse> data = new ArrayList<>();
        long difference = voteListRequest.getTo() - voteListRequest.getFrom();
        int div;
        if (difference < voteListRequest.getIntervals()) {
            div = Integer.parseInt(String.valueOf(difference));
        }
        else {
            div = voteListRequest.getIntervals();
        }

        List<String> artists = voteListRequest.getArtists();
        for (int i = 0; i < voteListRequest.getIntervals(); i++) {
            long dateTo = voteListRequest.getTo() - difference + (difference / div) * (i + 1);
            long dateFrom = voteListRequest.getFrom() + (difference / div) * i;
            data.add(new VoteStatisticsResponse(dateFrom, dateTo, 0));
            for (Vote vote: dataBase.getVotes()){
                if (artists.contains(vote.getArtist()) && dateTo >= vote.getSaveDate() && dateFrom < vote.getSaveDate()){
                    data.get(i).addVote();
                }
            }
        }
        return Map.ofEntries(Map.entry("data", data));
    }

    public ResponseEntity add(Vote vote) {
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
        if (!Pattern.matches("9\\d{9}", vote.getPhone())){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
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
