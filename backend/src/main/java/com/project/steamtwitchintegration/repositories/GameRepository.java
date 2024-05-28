package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.projections.GameProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long> {

    Page<GameProjection> findAllBy(Pageable pageable);

    Page<GameProjection> findAllByGameNameContainingIgnoreCase(String name, Pageable pageable);

}
