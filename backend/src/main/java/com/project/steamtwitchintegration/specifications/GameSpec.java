package com.project.steamtwitchintegration.specifications;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.PlayerPerspective;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class GameSpec {

    public static Specification<Game> hasPerspectives(List<Long> ids){
        return (((root, query, cb) -> {
            Join<Game, PlayerPerspective> join = root.join("perspectives");
            return join.get("id").in(ids);
        }));
    }

}
