package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.repositories.GameRecordRepository;
import com.project.steamtwitchintegration.repositories.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    public Page<Game> getLimitedGames(int limit){
        Pageable gameLimit = PageRequest.of(0, limit);
        return this.gameRepository.findAll(gameLimit);
    }

    public Game getGameByName(String gameName) {
        return this.gameRepository.findByGameName(gameName);
    }
}
