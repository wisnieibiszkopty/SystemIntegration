package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.TwitchGame;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TwitchGameRepository extends JpaRepository<TwitchGame, Long> {

}
