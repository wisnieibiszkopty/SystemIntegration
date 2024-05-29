package com.project.steamtwitchintegration;

import com.project.steamtwitchintegration.dataConvertion.*;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.repositories.GameRepository;
import com.project.steamtwitchintegration.repositories.SteamGameRepository;
import com.project.steamtwitchintegration.repositories.TwitchGameRepository;
import com.project.steamtwitchintegration.services.IGDBService;
import io.swagger.v3.core.util.Json;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final GameRepository gameRepository;
    private Parser parser;

    final SteamGameRepository steamRepository;
    private final TwitchGameRepository twitchRepository;
    private final IGDBService igdbService;

    public DatabaseInitializer(SteamGameRepository steamRepository, TwitchGameRepository twitchRepository, IGDBService igdbService, GameRepository gameRepository, Parser parser) {
        this.steamRepository = steamRepository;
        this.twitchRepository = twitchRepository;
        this.igdbService = igdbService;
        this.gameRepository = gameRepository;
        this.parser = parser;
    }

    @Override
    public void run(String... args) throws Exception {
        CsvParser csvParser = new CsvParser();
        JsonParser jsonParser = new JsonParser();
        XmlParser xmlParser = new XmlParser();

//        ClassPathResource steamFile = new ClassPathResource("data/exported/SteamGameData.csv");
//        ClassPathResource steamFile = new ClassPathResource("data/exported/SteamGameData.json");
        ClassPathResource steamFile = new ClassPathResource("data/exported/SteamGameData.xml");
//        ClassPathResource twitchFile = new ClassPathResource("data/exported/TwitchGameData.csv");
//        ClassPathResource twitchFile = new ClassPathResource("data/exported/TwitchGameData.json");
        ClassPathResource twitchFile = new ClassPathResource("data/exported/TwitchGameData.xml");
        Path steamPath = steamFile.getFile().toPath();
        Path twitchPath = twitchFile.getFile().toPath();
        log.info("Loading data... ");

//        csvParser.importData(steamPath.toString());
//        csvParser.importData(twitchPath.toString());
//
//        jsonParser.importData(steamPath.toString());
//        jsonParser.importData(twitchPath.toString());

        xmlParser.importData(steamPath.toString());
        xmlParser.importData(twitchPath.toString());

        parser.loadGames();
        log.info("Finished loading data");
//
        parser.showGames();
//
//        csvParser.showGames();
//        jsonParser.showGames();
//        xmlParser.showGames();

//        csvParser.exportData("C:\\Users\\pwins\\Documents\\SEM 6\\Szkielety programistyczne w aplikacjach internetowych\\SystemIntegration\\backend\\src\\main\\resources\\data\\exportedv26-05\\GameStats.csv", Parser.getGames());
//        jsonParser.exportData("C:\\Users\\pwins\\Documents\\SEM 6\\Szkielety programistyczne w aplikacjach internetowych\\SystemIntegration\\backend\\src\\main\\resources\\data\\exportedv26-05\\GameStats.json", Parser.getGames());
//        xmlParser.exportData("C:\\Users\\pwins\\Documents\\SEM 6\\Szkielety programistyczne w aplikacjach internetowych\\SystemIntegration\\backend\\src\\main\\resources\\data\\exportedv26-05\\GameStats.xml", Parser.getGames());

    }

}
