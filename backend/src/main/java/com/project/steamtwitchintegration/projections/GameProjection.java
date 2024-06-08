package com.project.steamtwitchintegration.projections;

import com.project.steamtwitchintegration.models.GameGenre;
import com.project.steamtwitchintegration.models.GameMode;
import com.project.steamtwitchintegration.models.PlayerPerspective;

import java.util.List;

public interface GameProjection {

    Long getId();
    String getCoverUrl();
    String getGameName();
    double getRating();
    int getRatingCount();
    double getTotalRating();
    int getTotalRatingCount();
    List<PlayerPerspective> getPerspectives();
    List<GameGenre> getGenres();
    List<GameMode> getModes();

}
