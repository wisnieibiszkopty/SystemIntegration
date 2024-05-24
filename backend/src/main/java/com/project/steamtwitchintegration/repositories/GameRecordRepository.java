package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRecordRepository extends JpaRepository<Game, Long> {
}
