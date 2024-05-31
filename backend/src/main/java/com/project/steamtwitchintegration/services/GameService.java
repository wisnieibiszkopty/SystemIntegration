package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dto.GamesInfoDto;
import com.project.steamtwitchintegration.models.*;
import com.project.steamtwitchintegration.projections.GameProjection;
import com.project.steamtwitchintegration.repositories.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

// TODO
// method to return list of games grouped by genres, name etc...
// method to return game records filtered by time and info only twitch, only steam, none?

@Service
public class GameService {
    private final GameRepository gameRepository;
    private final GameGenreRepository genreRepository;
    private final GameModeRepository modeRepository;
    private final PlayerPerspectiveRepository perspectiveRepository;

    public GameService(GameRepository gameRepository, GameGenreRepository genreRepository, GameModeRepository modeRepository, PlayerPerspectiveRepository perspectiveRepository) {
        this.gameRepository = gameRepository;
        this.genreRepository = genreRepository;
        this.modeRepository = modeRepository;
        this.perspectiveRepository = perspectiveRepository;
    }

    public GamesInfoDto getGamesInfo(){
        List<PlayerPerspective> perspectives = perspectiveRepository.findAll();
        List<GameMode> modes = modeRepository.findAll();
        List<GameGenre> genres = genreRepository.findAll();

        return new GamesInfoDto(perspectives, modes, genres);
    }

    // add finding by mode, genre, perspective etc.
    public Page<GameProjection> getAllGames(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gameRepository.findAllBy(pageable);
    }

    public Page<GameProjection> getGamesByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gameRepository.findAllByGameNameContainingIgnoreCase(name, pageable);
    }

}
