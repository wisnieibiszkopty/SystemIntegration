package com.project.steamtwitchintegration.models;

import lombok.Data;

@Data
public class SteamGame {

    private Long id;
    private String name;
    private String month;
    private String year;
    private double average;
    private double gain;
    private int peak;
    private String averagePeakPercent;

}
