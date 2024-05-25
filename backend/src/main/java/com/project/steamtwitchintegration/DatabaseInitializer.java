package com.project.steamtwitchintegration;

import com.project.steamtwitchintegration.dataConvertion.CsvParser;
import com.project.steamtwitchintegration.dataConvertion.Filetype;
import com.project.steamtwitchintegration.repositories.GameRepository;
import com.project.steamtwitchintegration.repositories.SteamGameRepository;
import com.project.steamtwitchintegration.repositories.TwitchGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

// TODO wyselekcjonowac reprezentatywna grupe gier do oceny statystycznej, moga sie przydac oceny z IGN albo metacritic lub igdb
// TODO wydzielic z twitcha gry ktore sa dostepne na steamie
// TODO ogarnc od steama klucz do API
// TODO ogarnac od twitcha dostep do API
@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {
    private final GameRepository gameRepository;
    private CsvParser csvParser;

    public DatabaseInitializer(GameRepository gameRepository, CsvParser csvParser) {
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
        csvParser.loadGames2();
        log.info("Finished loading data");
        System.out.println(csvParser.getGames().size());
        //gameRepository.saveAll(csvParser.getGames());
    }

}
