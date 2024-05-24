package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "gameRecord")
public class GameRecord {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "gameName")
    private Game game;

    private String year;
    private String month;
//    dane ze Steama
    private double steamAveragePlayers;
    private double steamGainPlayers;
    private int steamPeakPlayers;
    private int steamAvgPeakPerc;
//    dane z Twitcha
    private int twitchHoursWatched;
    private int twitchHoursStreamed;
    private int twitchPeakViewers;
    private int twitchPeakChannels;
    private int twitchStreamers;
    private int twitchAvgViewers;
    private int twitchAvgChannels;
    private double twitchAvgViewerRatio;
}
