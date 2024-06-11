package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.dto.GamesInfoDto;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.projections.GameProjection;
import com.project.steamtwitchintegration.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Game", description = "Endpoints for getting info about games")
@RestController
@RequestMapping("/api/games")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @Operation(summary = "Retrieve genres, modes and perspectives which game can have")
    @ApiResponse(responseCode = "200", content = {
        @Content(mediaType = "application/json", schema = @Schema(implementation = GamesInfoDto.class))
    })
    @GetMapping("/info")
    public ResponseEntity<GamesInfoDto> getGameInfo(){
        GamesInfoDto info = this.gameService.getGamesInfo();
        return ResponseEntity.ok(info);
    }

    @Operation(summary = "Retrieve paginated data about games")
    @ApiResponse(
        responseCode = "200", content = {
        @Content(mediaType = "application/json",
        schema = @Schema(implementation = GameProjection.class))
    })
    @Parameters({
        @Parameter(name = "page", description = "Page number, starting from 0"),
        @Parameter(name = "size", description = "Number of items per page"),
        @Parameter(name = "perspectives", description = "List of ids representing Player Perspectives by which games should be filtered"),
        @Parameter(name = "genres", description = "List of ids representing Game Genres by which games should be filtered"),
        @Parameter(name = "modes", description = "List of ids representing Game modes by which games should be filtered")
    })
    @GetMapping
    public ResponseEntity<Page<GameProjection>> getAllGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size,
            @RequestParam(required = false) List<Long> perspectives,
            @RequestParam(required = false) List<Long> modes,
            @RequestParam(required = false) List<Long> genres) {
        return ResponseEntity.ok(gameService.getAllGames(page, size, perspectives, modes, genres));
    }

    // pagination isn't handled on frontend so why bother
    @GetMapping("/all")
    public ResponseEntity<List<GameProjection>> getPlainGames(){
        return ResponseEntity.ok(gameService.getAllGamesAsList());
    }

    @Operation(
        summary = "Retrieve games by name",
        description = "Retrieve paginated data about games searched by name or it part"
    )
    @ApiResponse(
        responseCode = "200", content = {
        @Content(mediaType = "application/json",
        schema = @Schema(implementation = GameProjection.class))
    })
    @Parameters({
        @Parameter(name = "name", description = "Part of name of the game"),
        @Parameter(name = "page", description = "Page number, starting from 0"),
        @Parameter(name = "size", description = "Number of items per page")
    })
    @GetMapping("/byName/{name}")
    public Page<GameProjection> findGamesByName(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "25") int size){
        return gameService.getGamesByName(name, page, size);
    }

    @ApiResponses({})
    @GetMapping("/{gameId}")
    public Optional<Game> findGame(@PathVariable Long gameId){
        return gameService.getGame(gameId);
    }
}
