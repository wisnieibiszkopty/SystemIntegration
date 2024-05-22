package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.SteamGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteamGameRepository extends JpaRepository<SteamGame, Long> {

}
