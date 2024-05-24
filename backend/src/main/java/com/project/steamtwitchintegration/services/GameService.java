package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.repositories.GameRecordRepository;
import com.project.steamtwitchintegration.repositories.GameRepository;
import org.springframework.stereotype.Service;

import java.awt.font.TextHitInfo;
import java.util.List;

@Service
public class GameService {
    private GameRepository gameRepository;
    private GameRecordRepository gameRecordRepository;

    public GameService(GameRepository gameRepository, GameRecordRepository gameRecordRepository) {
        this.gameRepository = gameRepository;
        this.gameRecordRepository = gameRecordRepository;
    }

    public List<Game> getAllGames(){
        return this.gameRepository.findAll();
    }
    public Game getGameByName(String gameName) {
        return this.gameRepository.findByGameName(gameName);
    }
}
