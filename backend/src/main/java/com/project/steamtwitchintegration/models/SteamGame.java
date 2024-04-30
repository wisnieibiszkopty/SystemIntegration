package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SteamGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "game_month")
    private String month;
    @Column(name = "game_year")
    private String year;
    private double average;
    private double gain;
    private int peak;
    private String averagePeakPercent;

}
