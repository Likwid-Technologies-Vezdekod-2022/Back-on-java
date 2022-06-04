package ru.vezdecod.restback;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.vezdecod.restback.DB.LegacyInMemoryDataBase;

@SpringBootApplication
public class RestbackApplication {

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String[] artists = dotenv.get("artists").split(",");
        int sendLimit = Integer.parseInt(dotenv.get("send_limit"));
        int timeLimit = Integer.parseInt(dotenv.get("time_limit"));
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        for (String artist: artists) {
            dataBase.addArtist(artist);
        }
        dataBase.setSendLimit(sendLimit);
        dataBase.setTimeLimit(timeLimit);

        SpringApplication.run(RestbackApplication.class, args);
    }

}
