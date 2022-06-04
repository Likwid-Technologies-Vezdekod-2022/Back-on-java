package ru.vezdecod.restback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vezdecod.restback.entity.Vote;
import ru.vezdecod.restback.entity.VotesCollection;
import ru.vezdecod.restback.service.VoteService;

import java.util.List;
import java.util.Map;

@RequestMapping("/votes/")
@RestController
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    @GetMapping("/list")
    public List<VotesCollection> get_votes() {
        return voteService.list();
    }

    @PostMapping("/vote")
    public ResponseEntity vote(@RequestBody Vote vote) {
        return voteService.add(vote);
    }
}
