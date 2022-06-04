package ru.vezdecod.restback.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vezdecod.restback.service.ArtistService;

import java.util.List;

@RequestMapping("artists/")
@RestController
public class ArtistController {
    public final ArtistService artistService = new ArtistService();

    @PostMapping("artists_preload")
    public void set(@RequestBody List<String> artists){
        artistService.set(artists);
    }
}
