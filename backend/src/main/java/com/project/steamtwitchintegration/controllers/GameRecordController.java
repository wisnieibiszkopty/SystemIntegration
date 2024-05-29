package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.services.GameRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Game record", description = "Endpoint for getting time record with statistics about game")
@RestController
@RequestMapping("/api/records")
public class GameRecordController {

    private final GameRecordService gameRecordService;

    public GameRecordController(GameRecordService gameRecordService) {
        this.gameRecordService = gameRecordService;
    }

    // add filtering by date
    // and info about which data client wants
    @Operation(summary = "Retrieve all game records by game ID")
    @ApiResponses({

    })
    @GetMapping("/{id}")
    public List<GameRecord> getAllRecord(@PathVariable Long id){
        return this.gameRecordService.getGameRecords(id);
    }

}
