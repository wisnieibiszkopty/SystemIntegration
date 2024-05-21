package com.project.steamtwitchintegration.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TestModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String password;
}
