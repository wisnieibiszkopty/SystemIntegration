package com.project.steamtwitchintegration.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TwitchGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String month;
    private String year;
    private int hoursWatched;
    private int hoursStreamed;
    private int peakViewers;
    private int peakChannels;
    private int streamers;
    private int averageViewers;
    private int averageChannels;
    private double averageViewerRatio;

}
