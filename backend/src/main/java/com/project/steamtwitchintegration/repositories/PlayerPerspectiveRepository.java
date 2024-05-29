package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.PlayerPerspective;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerPerspectiveRepository extends JpaRepository<PlayerPerspective, Long> {
}
