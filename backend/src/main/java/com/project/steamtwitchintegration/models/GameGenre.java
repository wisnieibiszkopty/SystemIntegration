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
public class GameGenre {

    @Id
    private Long id;
    private String name;

    //@OneToMany(mappedBy = "genre", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ManyToMany
    @JoinTable(
            name = "games_genres",
            joinColumns = @JoinColumn(name = "game_genre_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @JsonBackReference
    private List<Game> games = new ArrayList<>();

    public GameGenre(){}

    public GameGenre(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addGame(Game game){
        this.games.add(game);
    }
}
