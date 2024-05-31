package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.dto.GamesInfoDto;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.projections.GameProjection;
import com.project.steamtwitchintegration.repositories.GameRecordRepository;
import com.project.steamtwitchintegration.services.GameService;
import com.project.steamtwitchintegration.models.Game;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO
// method to return list of games grouped by genres, name etc...
// method to return game records filtered by time and info only twitch, only steam, none?
// wrap objects with response object
// add documentation

@Tag(name = "Game", description = "Endpoints for getting info about games")
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Retrieve genres, modes and perspectives which game can have")
    @GetMapping("/info")
    public ResponseEntity<GamesInfoDto> getGameInfo(){
        GamesInfoDto info = this.gameService.getGamesInfo();
        return ResponseEntity.ok(info);
    }

    // add filtering by genre, mode, perspective
    @Operation(summary = "Retrieve paginated data about games")
    @ApiResponses({

    })
    @Parameters({
            @Parameter(name = "page", description = "Page number, starting from 0"),
            @Parameter(name = "size", description = "Number of items per page")
    })
    @GetMapping
    public Page<GameProjection> getAllGames(
            @Parameter(description = "Page number, starting from 0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page")
            @RequestParam(defaultValue = "25") int size) {
        return gameService.getAllGames(page, size);
    }

    @Operation(
        summary = "Retrieve games by name",
        description = "Retrieve paginated data about games searched by name or it part"
    )
    @ApiResponses({

    })
    @Parameters({
        @Parameter(name = "page", description = "Page number, starting from 0"),
        @Parameter(name = "size", description = "Number of items per page")
    })
    @GetMapping("/{name}")
    public Page<GameProjection> findGamesByName(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size){
        return gameService.getGamesByName(name, page, size);
    }
}
