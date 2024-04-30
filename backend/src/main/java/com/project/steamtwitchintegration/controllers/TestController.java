package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.models.TestModel;
import com.project.steamtwitchintegration.repositories.TestRepository;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TestRepository repository;

    @GetMapping("/test")
    public String test(){
        return "test";
    }

    @GetMapping("/one")
    public String getOne(){
        return "one";
    }

    @GetMapping("/all")
    public Iterable<TestModel> getAll(){
        return repository.findAll();
    }

    @PostMapping("/add")
    public TestModel addTest(){
        TestModel testModel = new TestModel();
        testModel.setName("test1");
        testModel.setPassword("test2");
        repository.save(testModel);
        return testModel;
    }

}
