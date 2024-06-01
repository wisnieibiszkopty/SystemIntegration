package com.project.steamtwitchintegration.specifications;

import com.project.steamtwitchintegration.dto.RecordFilterParam;
import com.project.steamtwitchintegration.models.GameRecord;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;


public class GameRecordSpec {

    public static Specification<GameRecord> getFilteredRecords(RecordFilterParam params){
        return ((root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(params.gameId() != null){
                predicates.add(cb.equal(root.get("game").get("id"), params.gameId()));
            }

            if(params.startDate() != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("timestamp"), params.startDate()));
            }

            if(params.endDate() != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("timestamp"), params.endDate()));
            }

            //query.orderBy(cb.desc(root.get("timestamp")));

            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }

}
