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
    private Parser parser;
    private final IGDBService igdbService;

    @Value("${LOAD_DATA:false}")
    private boolean loadDataOnStartup;
    @Value("${FETCH_FROM_API:false}")
    private boolean fetchDataFromApi;

    public DatabaseInitializer(IGDBService igdbService, Parser parser) {
        this.igdbService = igdbService;
        this.parser = parser;
    }

    @Override
    public void run(String... args) throws Exception {
        if(loadDataOnStartup){
            CsvParser csvParser = new CsvParser();

            InputStream steamStream = getClass().getResourceAsStream("/data/SteamModified.csv");
            InputStream twitchStream = getClass().getResourceAsStream("/data/Twitch_game_data.csv");

            log.info("Loading data... ");

            csvParser.importData(steamStream);
            csvParser.importData(twitchStream);

            parser.loadGames();

            log.info("Finished loading data");
        }
        if(fetchDataFromApi){
            log.info("Started fetching games info from api");
            igdbService.loadTwitchToken();
            igdbService.loadGameGeneralInfo();
            igdbService.loadGamesInfo();
            log.info("Finished fetching games info from api");
        }

    }

}
