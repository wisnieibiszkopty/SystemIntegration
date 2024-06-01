package com.project.steamtwitchintegration.dto;

import com.project.steamtwitchintegration.models.GameGenre;
import com.project.steamtwitchintegration.models.GameMode;
import com.project.steamtwitchintegration.models.PlayerPerspective;

import java.util.List;

public record GamesInfoDto(
   List<PlayerPerspective> perspectives,
   List<GameMode> modes,
   List<GameGenre> genres
) {}
