package com.project.steamtwitchintegration.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class GameMode{

    @Id
    private Long id;
    private String name;

    //@OneToMany(mappedBy = "mode", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "games_modes",
            joinColumns = @JoinColumn(name = "game_mode_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @JsonBackReference
    private List<Game> games = new ArrayList<>();

    public GameMode(){}

    public GameMode(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addGame(Game game){
        this.games.add(game);
    }
}
