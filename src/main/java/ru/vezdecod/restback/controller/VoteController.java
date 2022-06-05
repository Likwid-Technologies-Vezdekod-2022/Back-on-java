package ru.vezdecod.restback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vezdecod.restback.entity.Vote;
import ru.vezdecod.restback.requests.VoteListRequest;
import ru.vezdecod.restback.responses.VoteStatisticsResponse;
import ru.vezdecod.restback.responses.VotesListResponse;
import ru.vezdecod.restback.service.VoteService;

import java.util.ArrayList;
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
    public Map<String, List<VotesListResponse>> get_votes() {
        return voteService.list();
    }

    @GetMapping("/statistics")
    public Map<String, List<VoteStatisticsResponse>> get_statistics(
            @RequestParam(defaultValue = "0") long from,
            @RequestParam(defaultValue = "9223372036854775807") long to,
            @RequestParam(defaultValue = "10") int intervals,
            @RequestParam(defaultValue = "") String artists){
        VoteListRequest voteListRequest = new VoteListRequest(
                Long.parseLong(String.valueOf(from)),
                Long.parseLong(String.valueOf(to)),
                Integer.parseInt(String.valueOf(intervals)),
                artists);
        return voteService.statisticsResponses(voteListRequest);
    }

    @PostMapping("/vote")
    public ResponseEntity vote(@RequestBody Vote vote) {
        return voteService.add(vote);
    }
}
