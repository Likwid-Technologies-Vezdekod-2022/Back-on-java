package ru.vezdecod.restback.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.vezdecod.restback.entity.Vote;

import java.util.List;

@Service
public class VoteService {
    public List<Vote> list() {
        return List.of(
                new Vote("28391203", "sdadas@dfsfsd.com")
        );
    }

    public void add(Vote vote) {
        System.out.println(vote);
    }
}
