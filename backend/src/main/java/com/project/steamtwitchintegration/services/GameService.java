package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.projections.GameProjection;
import com.project.steamtwitchintegration.repositories.GameRecordRepository;
import com.project.steamtwitchintegration.repositories.GameRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// TODO
// method to return list of games grouped by genres, name etc...
// method to return game records filtered by time and info only twitch, only steam, none?

@Service
public class GameService {
    private final GameRepository gameRepository;

    public GameService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    // add finding by mode, genre, perspective etc.
    public Page<GameProjection> getAllGames(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gameRepository.findAllBy(pageable);
    }

    //  do czegos to sie przydaje?
    public Page<GameProjection> getGamesByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gameRepository.findAllByGameNameContainingIgnoreCase(name, pageable);
    }
    public Optional<Game> getGame(Long gameId){
        return gameRepository.findById(gameId);
    }
}
