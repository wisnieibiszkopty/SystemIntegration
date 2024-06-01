package com.project.steamtwitchintegration.dto;

import com.project.steamtwitchintegration.models.GameGenre;
import com.project.steamtwitchintegration.models.GameMode;
import com.project.steamtwitchintegration.models.PlayerPerspective;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class GameDto{
    private Long id;
    private String gameName;
    private String coverUrl;
    private double rating;
    private int ratingCount;
    private double totalRating;
    private int totalRatingCount;
    private List<PlayerPerspective> perspectives;
    private List<GameGenre> genres;
    private List<GameMode> modes;
}
