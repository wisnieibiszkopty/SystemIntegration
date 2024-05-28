package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.repositories.GameRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameRecordService {

    private final GameRecordRepository recordRepository;

    public GameRecordService(GameRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    // add time
    // to sort by time we need to change date format from string to int or just make date field
    public List<GameRecord> getGameRecords(Long gameId){
        return this.recordRepository.findAllByGameId(gameId);
    }
}
