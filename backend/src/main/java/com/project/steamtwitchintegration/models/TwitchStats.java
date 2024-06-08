package com.project.steamtwitchintegration.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "twitch_stats")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TwitchStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int twitchHoursWatched;
    private int twitchHoursStreamed;
    private int twitchPeakViewers;
    private int twitchPeakChannels;
    private int twitchStreamers;
    private int twitchAvgViewers;
    private int twitchAvgChannels;
    private double twitchAvgViewerRatio;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameRecord_id")
    @JsonBackReference
    private GameRecord record;
}
