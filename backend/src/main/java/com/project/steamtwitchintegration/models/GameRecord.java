package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

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

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private SteamStats steamStats;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private TwitchStats twitchStats;
}
