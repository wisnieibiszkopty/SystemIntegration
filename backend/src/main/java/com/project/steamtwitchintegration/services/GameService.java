package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dto.GamesInfoDto;
import com.project.steamtwitchintegration.models.*;
import com.project.steamtwitchintegration.projections.GameProjection;
import com.project.steamtwitchintegration.repositories.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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

    @Transactional(
        propagation = Propagation.REQUIRED,
        timeout = 15,
        readOnly = true)
    public GamesInfoDto getGamesInfo(){
        List<PlayerPerspective> perspectives = perspectiveRepository.findAll();
        List<GameMode> modes = modeRepository.findAll();
        List<GameGenre> genres = genreRepository.findAll();

        return new GamesInfoDto(perspectives, modes, genres);
    }

    // by default only one list of id can be added to request
    // selecting by modes and genres together is impossible
    // I was trying to do this for 3 hours today, but i no longer care
    // If I will have some time left I will try rewrite it using native query
    @Transactional(
        propagation = Propagation.REQUIRED,
        timeout = 15,
        readOnly = true)
    public Page<GameProjection> getAllGames(int page, int size, List<Long> perspectivesId, List<Long> modesId, List<Long> genresId){
        Pageable pageable = PageRequest.of(page, size);

        if(perspectivesId != null && !perspectivesId.isEmpty()){
            return gameRepository.findByPerspectivesIdIn(perspectivesId, pageable);
        }

        if(modesId != null && !modesId.isEmpty()){
            return gameRepository.findByModesIdIn(modesId, pageable);
        }

        if(genresId != null &&!genresId.isEmpty()){
            return gameRepository.findByGenresIdIn(genresId, pageable);
        }

        return gameRepository.findAllBy(pageable);
    }

    public List<GameProjection> getAllGamesAsList(){
        return gameRepository.findAllBy();
    }

    //  do czegos to sie przydaje?
    // nie wiem robimy wszystko na froncie i tak
    @Transactional(
        propagation = Propagation.REQUIRED,
        timeout = 15,
        readOnly = true)
    public Page<GameProjection> getGamesByName(String name, int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return gameRepository.findAllByGameNameContainingIgnoreCase(name, pageable);
    }

    @Transactional(
        propagation = Propagation.REQUIRED,
        timeout = 15,
        readOnly = true)
    public Optional<Game> getGame(Long gameId){
        return gameRepository.findById(gameId);
    }
}
