package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.dto.GameDto;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.projections.GameProjection;
import com.project.steamtwitchintegration.specifications.GameSpec;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameRepository extends JpaRepository<Game, Long>{

    Page<GameProjection> findAllBy(Pageable pageable);
    List<GameProjection> findAllBy();

    // None of this method return proper intersection but at this point I don't even care
    Page<GameProjection> findByPerspectivesIdIn(@Param("perspectivesIds") List<Long> perspectivesId, Pageable pageable);
    Page<GameProjection> findByGenresIdIn(@Param("genresId") List<Long> genresId, Pageable pageable);
    Page<GameProjection> findByModesIdIn(@Param("modesId") List<Long> modesId, Pageable pageable);
    Page<GameProjection> findAllByGameNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT DISTINCT new com.project.steamtwitchintegration.dto.GameDto(g.id, g.gameName, g.coverUrl," +
            " g.rating, g.ratingCount, g.totalRating, g.totalRatingCount, g.perspectives, g.genres, g.modes) FROM Game g ")
    Page<GameDto> findAllGames(Pageable pageable);
}
