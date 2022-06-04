package ru.vezdecod.restback.service;

import org.springframework.stereotype.Service;
import ru.vezdecod.restback.DB.LegacyInMemoryDataBase;

import java.util.List;

@Service
public class ArtistService {
    public void set(List<String> artists){
        LegacyInMemoryDataBase dataBase = LegacyInMemoryDataBase.getInstance();
        dataBase.setArtists(artists);
    }
}
