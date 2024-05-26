package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class GameMode{

    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "mode", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Game> games;

    public GameMode(){}

    public GameMode(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
