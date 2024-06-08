package com.project.steamtwitchintegration.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity(name = "gameRecord")
public class GameRecord {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "game_id")
    @JsonBackReference
    private Game game;

    private String timestamp;
    private Date time;
    @Column(name = "record_year")
    private String year;
    @Column(name = "record_month")
    private String month;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private SteamStats steamStats;

    @OneToOne(mappedBy = "record", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private TwitchStats twitchStats;

    public void setTime(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.valueOf(year), Integer.valueOf(month) - 1, 1);
        this.time = calendar.getTime();
    }
}
