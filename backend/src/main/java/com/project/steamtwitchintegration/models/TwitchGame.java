package com.project.steamtwitchintegration.models;

import lombok.Data;

@Data
public class TwitchGame {

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
