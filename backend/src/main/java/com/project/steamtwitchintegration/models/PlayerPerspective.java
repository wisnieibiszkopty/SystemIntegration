package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
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
    private List<Game> games;

    public PlayerPerspective(){}

    public PlayerPerspective(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
