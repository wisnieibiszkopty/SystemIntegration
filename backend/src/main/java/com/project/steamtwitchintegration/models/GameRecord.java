package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "gameRecord")
public class GameRecord {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    private Game game;

    @Column(name = "record_year")
    private String year;
    @Column(name = "record_month")
    private String month;
    // Steam data
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "steam_stats_id", referencedColumnName = "id")
//    private SteamStats steamStats;
    private double steamAveragePlayers;
    private double steamGainPlayers;
    private int steamPeakPlayers;
    private String steamAvgPeakPerc;
    // Twitch data
//    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "twitch_stats_id", referencedColumnName = "id")
//    private TwitchStats twitchStats;
    private int twitchHoursWatched;
    private int twitchHoursStreamed;
    private int twitchPeakViewers;
    private int twitchPeakChannels;
    private int twitchStreamers;
    private int twitchAvgViewers;
    private int twitchAvgChannels;
    private double twitchAvgViewerRatio;
}
