package com.project.steamtwitchintegration.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@ToString
public class PlayerPerspective {

    @Id
    private Long id;
    private String name;

    //@OneToMany(mappedBy = "perspective", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @ManyToMany
    @JoinTable(
            name = "games_perspectives",
            joinColumns = @JoinColumn(name = "player_perspective_id"),
            inverseJoinColumns = @JoinColumn(name = "game_id")
    )
    @JsonBackReference
    private List<Game> games = new ArrayList<>();

    public PlayerPerspective(){}

    public PlayerPerspective(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addGame(Game game){
        this.games.add(game);
    }
}
