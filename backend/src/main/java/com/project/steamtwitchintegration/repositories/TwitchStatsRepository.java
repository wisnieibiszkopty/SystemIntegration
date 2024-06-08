package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.TwitchStats;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitchStatsRepository extends JpaRepository<TwitchStats, Long> {
}
