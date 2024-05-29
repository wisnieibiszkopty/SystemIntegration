package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

    public List<GameRecord> findAllByGameId(Long gameId);

}
