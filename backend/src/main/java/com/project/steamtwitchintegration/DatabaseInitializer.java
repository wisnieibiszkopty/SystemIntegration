package com.project.steamtwitchintegration;

import com.project.steamtwitchintegration.dataConvertion.CsvParser;
import com.project.steamtwitchintegration.dataConvertion.Filetype;
import com.project.steamtwitchintegration.repositories.SteamGameRepository;
import com.project.steamtwitchintegration.repositories.TwitchGameRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;

// TODO wyselekcjonowac reprezentatywna grupe gier do oceny statystycznej, moga sie przydac oceny z IGN albo metacritic lub igdb
// TODO wydzielic z twitcha gry ktore sa dostepne na steamie
// TODO ogarnc od steama klucz do API
// TODO ogarnac od twitcha dostep do API
@Slf4j
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final SteamGameRepository steamRepository;
    private final TwitchGameRepository twitchRepository;

    public DatabaseInitializer(SteamGameRepository steamRepository, TwitchGameRepository twitchRepository) {
        this.steamRepository = steamRepository;
        this.twitchRepository = twitchRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        CsvParser csvParser = new CsvParser();

        // Loading data about games from Steam
        ClassPathResource steamResource = new ClassPathResource("data/SteamModified.csv");
        Path steamPath = steamResource.getFile().toPath();
        csvParser.importData(steamPath.toString());

//		csvParser.exportData("src/main/resources/data/SteamTEST.csv", Filetype.CSV);
//		csvParser.exportData("src/main/resources/data/SteamTEST.json", Filetype.JSON);
//		csvParser.exportData("src/main/resources/data/SteamTEST.xml", Filetype.XML);

        csvParser.loadSteamGames();
        steamRepository.saveAll(csvParser.getSteamGames());

        log.info("Loaded steam games");
//        System.out.println(csvParser);

        // Loading data about games form Twitch
        ClassPathResource twitchResource = new ClassPathResource("data/Twitch_game_data.csv");
        Path twitchPath = twitchResource.getFile().toPath();
        csvParser.importData(twitchPath.toString());

//		csvParser.exportData("src/main/resources/data/TwitchTEST.csv", Filetype.CSV);
//		csvParser.exportData("src/main/resources/data/TwitchTEST.json", Filetype.JSON);
//		csvParser.exportData("src/main/resources/data/TwitchTEST.xml", Filetype.XML);
        csvParser.loadTwitchGames();
        twitchRepository.saveAll(csvParser.getTwitchGames());

        log.info("Loaded twitch games");
//        System.out.println(csvParser);

//        csvParser.showgames();
    }

}
