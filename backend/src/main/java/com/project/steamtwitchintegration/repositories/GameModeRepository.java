package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.GameMode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameModeRepository extends JpaRepository<GameMode, Long> {
}
