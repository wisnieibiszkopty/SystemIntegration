package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.models.SteamGame;
import com.project.steamtwitchintegration.repositories.SteamGameRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SteamGameService {

    private final SteamGameRepository steamRepository;

    public SteamGameService(SteamGameRepository steamRepository) {
        this.steamRepository = steamRepository;
    }

    public List<SteamGame> getAllGames(){
        return this.steamRepository.findAll();
    }

    public SteamGame getGame(Long id){
        return this.steamRepository.findById(id).orElseThrow();
    }

}
