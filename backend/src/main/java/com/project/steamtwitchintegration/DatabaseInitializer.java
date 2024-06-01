package com.project.steamtwitchintegration;

import com.project.steamtwitchintegration.dataConvertion.*;
import com.project.steamtwitchintegration.repositories.GameRepository;
import com.project.steamtwitchintegration.services.IGDBService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final GameRepository gameRepository;
    private Parser parser;
    private final IGDBService igdbService;

    @Value("${LOAD_DATA:false}")
    private boolean loadDataOnStartup;

    public DatabaseInitializer(IGDBService igdbService, GameRepository gameRepository, Parser parser) {
        this.igdbService = igdbService;
        this.gameRepository = gameRepository;
        this.parser = parser;
    }

    @Override
    public void run(String... args) throws Exception {
        if(loadDataOnStartup){
            igdbService.loadTwitchToken();
            igdbService.loadGameGeneralInfo();

            CsvParser csvParser = new CsvParser();

            InputStream steamStream = getClass().getResourceAsStream("/data/SteamModified.csv");
            InputStream twitchStream = getClass().getResourceAsStream("/data/Twitch_game_data.csv");

            log.info("Loading data... ");

            csvParser.importData(steamStream);
            csvParser.importData(twitchStream);

            parser.loadGames();
            igdbService.loadGamesInfo();

            log.info("Finished loading data");
        }
    }
}
