package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String gameName;
    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GameRecord> gameRecords = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player_perspective_id")
    private PlayerPerspective perspective;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_genre_id")
    private GameGenre genre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_mode_id")
    private GameMode mode;

    public void addGameRecord(GameRecord gameRecord){
        this.gameRecords.add(gameRecord);
    }
}
