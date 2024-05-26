package com.project.steamtwitchintegration;

import com.project.steamtwitchintegration.dataConvertion.CsvParser;
import com.project.steamtwitchintegration.dataConvertion.Filetype;
import com.project.steamtwitchintegration.repositories.GameRepository;
import com.project.steamtwitchintegration.repositories.SteamGameRepository;
import com.project.steamtwitchintegration.repositories.TwitchGameRepository;
import com.project.steamtwitchintegration.services.IGDBService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final GameRepository gameRepository;
    private CsvParser csvParser;

    final SteamGameRepository steamRepository;
    private final TwitchGameRepository twitchRepository;
    private final IGDBService igdbService;

    public DatabaseInitializer(SteamGameRepository steamRepository, TwitchGameRepository twitchRepository, IGDBService igdbService, GameRepository gameRepository, CsvParser csvParser) {
        this.steamRepository = steamRepository;
        this.twitchRepository = twitchRepository;
        this.igdbService = igdbService;
        this.gameRepository = gameRepository;
        this.csvParser = csvParser;
    }

    @Override
    public void run(String... args) throws Exception {
        //CsvParser csvParser = new CsvParser();
        ClassPathResource steamFile = new ClassPathResource("data/SteamModified.csv");
        ClassPathResource twitchFile = new ClassPathResource("data/Twitch_game_data.csv");
        Path steamPath = steamFile.getFile().toPath();
        Path twitchPath = twitchFile.getFile().toPath();
        log.info("Loading data... ");
        csvParser.importData(steamPath.toString());
        csvParser.importData(twitchPath.toString());
        csvParser.loadGames3();
        log.info("Finished loading data");
        //gameRepository.saveAll(csvParser.getGames());
    }

}
