package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.SteamStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SteamStatsRepository extends JpaRepository<SteamStats, Long> {
}
