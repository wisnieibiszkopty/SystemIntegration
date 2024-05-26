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

    @OneToMany(mappedBy = "perspective", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Game> games;

    public PlayerPerspective(){}

    public PlayerPerspective(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
