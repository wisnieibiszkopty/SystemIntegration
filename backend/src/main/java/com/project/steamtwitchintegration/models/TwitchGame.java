package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TwitchGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @Column(name = "game_month")
    private String month;
    @Column(name = "game_year")
    private String year;
    private int hoursWatched;
    private int hoursStreamed;
    private int peakViewers;
    private int peakChannels;
    private int streamers;
    private int averageViewers;
    private int averageChannels;
    private double averageViewerRatio;

}
