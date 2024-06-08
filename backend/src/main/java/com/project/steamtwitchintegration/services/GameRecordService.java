package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dto.RecordFilterParam;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.repositories.GameRecordRepository;
import com.project.steamtwitchintegration.specifications.GameRecordSpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.rmi.server.ExportException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class GameRecordService {

    private final GameRecordRepository recordRepository;

    public GameRecordService(GameRecordRepository recordRepository) {
        this.recordRepository = recordRepository;
    }

    // why it is so slow
    public List<GameRecord> getGameRecords(Long gameId, String startDate, String endDate){
        Specification<GameRecord> spec = GameRecordSpec.getFilteredRecords(new RecordFilterParam(gameId, startDate, endDate));
        return this.recordRepository.findAll(spec);
    }

}
