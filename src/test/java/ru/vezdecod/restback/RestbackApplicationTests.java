package ru.vezdecod.restback;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.vezdecod.restback.DB.LegacyInMemoryDataBase;
import ru.vezdecod.restback.controller.VoteController;
import ru.vezdecod.restback.entity.Vote;

import java.awt.geom.QuadCurve2D;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
class CLITests {
    @Autowired
    private VoteController controller;
    @Autowired
    private MockMvc mockMvc;
    public int generateRandomIntInRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
    public static String voteToJson(Vote vote) {
        return "{\"artist\":"+vote.getArtist()+",\"phone\":"+vote.getPhone()+"}";
    }
    @Test
    public void testVote() throws Exception {
        Dotenv dotenv = Dotenv.load();
        String[] env_artists = dotenv.get("artists").split(",");
        int sendLimit = Integer.parseInt(dotenv.get("send_limit"));
        int timeLimit = Integer.parseInt(dotenv.get("time_limit"));
        int requestsAmount = Integer.parseInt(dotenv.get("requests_amount"));
        int userAmount = Integer.parseInt(dotenv.get("user_amount"));
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        for (String artist: env_artists) {
            dataBase.addArtist(artist);
        }
        dataBase.setSendLimit(sendLimit);
        dataBase.setTimeLimit(timeLimit);
        dataBase.setUserAmount(userAmount);
        dataBase.setRequestsAmount(requestsAmount);
        List<Vote> votes = new ArrayList<>();
        List<String> artists = dataBase.getArtists();

        for (int i = 0; i < dataBase.getUserAmount(); i++){
            votes.add(new Vote(
                    "9" + generateRandomIntInRange(100000000, 999999999),
                    artists.get(generateRandomIntInRange(0, artists.size()-1))
            ));
        }
        int success = 0;
        for (int i = 0; i < dataBase.getRequestsAmount(); i++){
            String json_vote = voteToJson(votes.get(generateRandomIntInRange(0, votes.size()-1)));
            long start = System.currentTimeMillis();
            try {
                this.mockMvc.perform(post("/votes/vote")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_vote)
                ).andExpect(MockMvcResultMatchers.status().isCreated());
                success ++;
            }
            catch (AssertionError e) {

            }
            long end = System.currentTimeMillis();
            dataBase.addTimeList(end-start);
        }
        List<Long> times = dataBase.getTimeList();
        Collections.sort(times);
        dataBase.setTimeList(times);
        long total = dataBase.getTimeList().stream().mapToLong(Long::longValue).sum();
        System.out.printf("Total: %d ms \n",  total);
        System.out.printf("Min time: %d ms \n", Collections.min(dataBase.getTimeList()));
        System.out.printf("Max time: %d ms \n", Collections.max(dataBase.getTimeList()));
        OptionalDouble ave_MARIA = dataBase.getTimeList().stream().mapToLong(Long::longValue).average();
        if (ave_MARIA.isPresent()){
            System.out.printf("Average: %.2f ms \n",  ave_MARIA.getAsDouble());
        }
        System.out.printf("Average requests per second: %.2f \n", Double.valueOf(dataBase.getRequestsAmount()) / (Double.valueOf(total) / 1000));
        System.out.printf("10 percent: %d ms \n", dataBase.getTimeList().get(dataBase.getTimeList().size()/100*90));
        System.out.printf("25 percent: %d ms \n", dataBase.getTimeList().get(dataBase.getTimeList().size()/100*75));
        System.out.printf("50 percent: %d ms \n", dataBase.getTimeList().get(dataBase.getTimeList().size()/100*50));
        System.out.printf("75 percent: %d ms \n", dataBase.getTimeList().get(dataBase.getTimeList().size()/100*25));
        System.out.printf("90 percent: %d ms \n", dataBase.getTimeList().get(dataBase.getTimeList().size()/100*10));
        System.out.printf("95 percent: %d ms \n", dataBase.getTimeList().get(dataBase.getTimeList().size()/100*5));
        System.out.printf("99 percent: %d ms \n", dataBase.getTimeList().get(dataBase.getTimeList().size()/100));
        System.out.printf("201: %d \n", success);
        System.out.printf("429: %d \n", dataBase.getRequestsAmount()-success);
    }

}
