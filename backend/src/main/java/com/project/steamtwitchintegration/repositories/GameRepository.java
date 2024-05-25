package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {
    public Game findByGameName(String gameName);

}
