package com.project.steamtwitchintegration.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Game {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    private String gameName;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<GameRecord> gameRecords = new ArrayList<>();

    private String coverUrl;
    private double rating;
    private int ratingCount;
    private double totalRating;
    private int totalRatingCount;

    @ManyToMany(mappedBy = "games", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<PlayerPerspective> perspectives = new ArrayList<>();

    @ManyToMany(mappedBy = "games", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<GameGenre> genres = new ArrayList<>();

    @ManyToMany(mappedBy = "games", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<GameMode> modes = new ArrayList<>();

    public void addGameRecord(GameRecord gameRecord){
        this.gameRecords.add(gameRecord);
    }

    public void addPerspective(PlayerPerspective perspective){
        this.perspectives.add(perspective);
    }

    public void addGenre(GameGenre genre){
        this.genres.add(genre);
    }

    public void addMode(GameMode mode){
        this.modes.add(mode);
    }
}
