package com.project.steamtwitchintegration;

import com.fasterxml.jackson.databind.JsonNode;
import com.project.steamtwitchintegration.dataConvertion.CsvParser;
import com.project.steamtwitchintegration.dataConvertion.Filetype;
import com.project.steamtwitchintegration.models.GameGenre;
import com.project.steamtwitchintegration.models.GameMode;
import com.project.steamtwitchintegration.models.PlayerPerspective;
import com.project.steamtwitchintegration.repositories.GameRepository;
import com.project.steamtwitchintegration.services.IGDBService;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final GameRepository gameRepository;
    private CsvParser csvParser;
    private final IGDBService igdbService;

    public DatabaseInitializer(IGDBService igdbService, GameRepository gameRepository, CsvParser csvParser) {
        this.igdbService = igdbService;
        this.gameRepository = gameRepository;
        this.csvParser = csvParser;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // expensvie methods
        //igdbService.loadTwitchToken();
        //igdbService.loadGameGeneralInfo();

        //CsvParser csvParser = new CsvParser();
//        ClassPathResource steamFile = new ClassPathResource("data/SteamModified.csv");
//        ClassPathResource twitchFile = new ClassPathResource("data/Twitch_game_data.csv");
//        Path steamPath = steamFile.getFile().toPath();
//        Path twitchPath = twitchFile.getFile().toPath();

        // in jar files don't exist
        //InputStream steamStream = getClass().getResourceAsStream("/data/SteamModified.csv");
        //InputStream twitchStream = getClass().getResourceAsStream("/data/Twitch_game_data.csv");

        log.info("Loading data... ");
        //csvParser.importData(steamPath.toString());
        //csvParser.importData(twitchPath.toString());
        //csvParser.importData2(steamStream);
        //csvParser.importData2(twitchStream);

        //csvParser.loadGames3();

        log.info("Finished loading data");
        //gameRepository.saveAll(csvParser.getGames());

        // expensive method
        //igdbService.loadGamesInfo();
    }

    public void loadGeneralGamesData(){
        // Calls to api to get general info about games
        // Shouldn't be used while testing
//        igdbService.loadGameGeneralInfo("game_modes", new GameMode());
//        igdbService.loadGameGeneralInfo("genres", new GameGenre());
//        igdbService.loadGameGeneralInfo("player_perspectives", new PlayerPerspective());

    }

}
