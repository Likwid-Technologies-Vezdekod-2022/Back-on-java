package ru.vezdecod.restback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vezdecod.restback.entity.Vote;
import ru.vezdecod.restback.service.VoteService;

import java.util.List;

@RequestMapping("/votes/")
@RestController
public class VoteController {

    private final VoteService voteService;

    public VoteController(VoteService voteService) {
        this.voteService = voteService;
    }


    @GetMapping("/list")
    public List<Vote> get_votes() {
        return voteService.list();
    }

    @PostMapping("/vote")
    public void vote(@RequestBody Vote vote) {
        voteService.add(vote);
    }
}
