package com.project.steamtwitchintegration.repositories;

import com.project.steamtwitchintegration.models.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<TestModel, Integer> {
}
