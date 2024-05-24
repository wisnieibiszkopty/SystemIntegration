package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.services.GameService;
import com.project.steamtwitchintegration.models.Game;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation( summary = "Get all games (summary model of SteamGame and TwitchGame)")
    @GetMapping
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @Operation
    @GetMapping("/{name}")
    public Game getGame(@PathVariable String name) {
        return gameService.getGameByName(name);
    }
}
