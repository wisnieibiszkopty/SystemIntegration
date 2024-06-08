package com.project.steamtwitchintegration.dto;

public record RecordFilterParam(
        Long gameId,
        String startDate,
        String endDate
) {}
