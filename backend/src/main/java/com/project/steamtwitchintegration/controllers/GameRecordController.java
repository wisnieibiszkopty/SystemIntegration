package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.projections.GameProjection;
import com.project.steamtwitchintegration.services.GameRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "Game record", description = "Endpoint for getting time record with statistics about game")
@RestController
@RequestMapping("/api/records")
public class GameRecordController {

    private final GameRecordService gameRecordService;

    public GameRecordController(GameRecordService gameRecordService) {
        this.gameRecordService = gameRecordService;
    }

    @Operation(summary = "Retrieve all game records by game ID")
    @ApiResponse(
        responseCode = "200", content = {
        @Content(mediaType = "application/json",
        schema = @Schema(implementation = GameRecord.class))
    })
    @Parameters({
        @Parameter(name = "id", description = "Id of games for which records should be returned"),
        @Parameter(name = "startDate", description = "yyyy-mm date from which records should be returned"),
        @Parameter(name = "endDate", description = "yyyy-mm to from which records should be returned")
    })
    @GetMapping("/{id}")
    public List<GameRecord> getAllRecord(
        @PathVariable Long id,
        @RequestParam(required = false) String startDate,
        @RequestParam(required = false) String endDate
    ){
        return this.gameRecordService.getGameRecords(id, startDate, endDate);
    }

}
