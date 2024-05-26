package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.GameGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameGenreRepository extends JpaRepository<GameGenre, Long> {
}
