package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.services.GameService;
import com.project.steamtwitchintegration.models.Game;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

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
    // request param to limit number of returned games
    public List<Game> getAllGames() {
        return gameService.getAllGames();
    }

    @GetMapping("/limit/{limit}")
    public Page<Game> getLimitedGames(@PathVariable int limit){
        return this.gameService.getLimitedGames(limit);
    }

    @Operation
    @GetMapping("/{name}")
    public Game getGame(@PathVariable String name) {
        return gameService.getGameByName(name);
    }
}
