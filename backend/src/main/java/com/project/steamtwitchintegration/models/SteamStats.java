package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@Entity
@Table(name = "steam_stats")
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SteamStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double steamAveragePlayers;
    private double steamGainPlayers;
    private int steamPeakPlayers;
    private String steamAvgPeakPerc;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gameRecord_id")
    private GameRecord record;

}
