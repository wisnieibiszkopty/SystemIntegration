package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.models.SteamGame;
import com.project.steamtwitchintegration.services.SteamGameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/steam")
public class SteamGameController {

    private final SteamGameService steamService;

    public SteamGameController(SteamGameService steamService) {
        this.steamService = steamService;
    }

    @GetMapping
    public List<SteamGame> getAllSteamGames(){
        return steamService.getAllGames();
    }

    @GetMapping("/{id}")
    public SteamGame getGame(@PathVariable Long id){
        return steamService.getGame(id);
    }

}
