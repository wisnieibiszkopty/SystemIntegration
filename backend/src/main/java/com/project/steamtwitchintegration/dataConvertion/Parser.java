package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.*;
import com.project.steamtwitchintegration.repositories.GameRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class Parser {
    @Getter
    @Setter
    public static List<Game> games = new ArrayList<>();
    @Getter
    @Setter
    public static List<SteamGame> steamGames;
    @Getter
    @Setter
    public static List<TwitchGame> twitchGames;

    @Autowired
    public GameRepository gameRepository;

    /**
     * Function loading Game objects to DataBase by Repository
     */
    public void loadGames() {
        long startTime = System.nanoTime();
        log.info("Started adding GameRecords");
//        CZAS: 41.34
//        steamGames.forEach(steamGame -> {
//            GameRecord record = new GameRecord();
//            twitchGames.forEach(twitchGame -> {
//                if (twitchGame.getTitle().equals(steamGame.getName())
//                        && twitchGame.getMonth().equals(steamGame.getMonth())
//                        && twitchGame.getYear().equals(steamGame.getYear())){
//                    record.setYear(steamGame.getYear());
//                    record.setMonth(steamGame.getMonth());
//
//                    SteamStats steamStats = getSteamStats(steamGame);
//                    steamStats.setRecord(record);
//                    record.setSteamStats(steamStats);
//
//                    TwitchStats twitchStats = getTwitchStats(twitchGame);
//                    twitchStats.setRecord(record);
//                    record.setTwitchStats(twitchStats);
//                    games.forEach(game -> {
//                        if (game.getGameName().equals(twitchGame.getTitle())) {
//                            record.setGame(game);
//                            game.getGameRecords().add(record);
//                            log.info("Added record to " + game.getGameName());
//                        }
//                    });
//                }
//            });
//        });

//        CZAS: 38.20
        steamGames.forEach(steamGame -> {
            GameRecord record = new GameRecord();
            twitchGames.stream()
                    .filter(twitchGame ->
                            twitchGame.getTitle().equals(steamGame.getName())
                                    && twitchGame.getMonth().equals(steamGame.getMonth())
                                    && twitchGame.getYear().equals(steamGame.getYear())
                    )
                    .findFirst()
                    .ifPresent(twitchGame -> {
                        addGameByName(twitchGame.getTitle());
                        record.setYear(steamGame.getYear());
                        record.setMonth(steamGame.getMonth());

                        SteamStats steamStats = getSteamStats(steamGame);
                        steamStats.setRecord(record);
                        record.setSteamStats(steamStats);

                        TwitchStats twitchStats = getTwitchStats(twitchGame);
                        twitchStats.setRecord(record);
                        record.setTwitchStats(twitchStats);

                        games.stream()
                                .filter(game -> game.getGameName().equals(twitchGame.getTitle()))
                                .findFirst()
                                .ifPresent(game -> {
                                    record.setGame(game);
                                    game.getGameRecords().add(record);
                                    log.info("Added record to " + game.getGameName());
                                });
                    });

        });
        long endTime = System.nanoTime();

//        szybsze od stream.filter ale minimalnie wolniejszy of for
        games.removeIf(game -> game.getGameRecords().size() < 15);
        System.out.println("CZAS: " + (endTime-startTime) / 1e9 );
        gameRepository.saveAll(games);
        log.info("Finished adding game records");
    }

    public void addGameByName(String name){
        if(games.stream().anyMatch(game -> game.getGameName().equals(name))){
            //log.info("Game already exists");
            return;
        }

        Game game = new Game();
        game.setGameName(name);
        games.add(game);
        log.info("Added: " + game.getGameName());
    }
    private TwitchStats getTwitchStats(TwitchGame twitchGame){
        return TwitchStats
                .builder()
                .twitchAvgChannels(twitchGame.getAverageChannels())
                .twitchAvgViewers(twitchGame.getAverageViewers())
                .twitchHoursStreamed(twitchGame.getHoursStreamed())
                .twitchHoursWatched(twitchGame.getHoursWatched())
                .twitchPeakChannels(twitchGame.getPeakChannels())
                .twitchPeakViewers(twitchGame.getPeakViewers())
                .twitchStreamers(twitchGame.getStreamers())
                .twitchAvgViewerRatio(twitchGame.getAverageViewerRatio())
                .build();
    }

    private SteamStats getSteamStats(SteamGame steamGame){
        return SteamStats
                .builder()
                .steamAveragePlayers(steamGame.getAverage())
                .steamPeakPlayers(steamGame.getPeak())
                .steamAvgPeakPerc(steamGame.getAveragePeakPercent())
                .steamGainPlayers(steamGame.getGain())
                .build();
    }
    public void showGames() {
        System.out.println("ILOSC GIER: " + games.size());
        for (Game g : games) {
            if (g.getGameRecords().isEmpty()) {
                System.out.println("\nGRA: " + g.getGameName() + " - nie posiada żadnych danych z Twitch'a!");
            } else {
                System.out.println("\n\tGRA:" + g.getGameName());
                System.out.println("Ilość wpisów z Twitcha: " + g.getGameRecords().size());
            }
        }
    }
    /**
     * Function convert month from format "01" to "January " etc.
     */
    public String monthConvert(String month) {
        return switch (month) {
            case "01" -> "January ";
            case "02" -> "February ";
            case "03" -> "March ";
            case "04" -> "April ";
            case "05" -> "May ";
            case "06" -> "June ";
            case "07" -> "July ";
            case "08" -> "August ";
            case "09" -> "September ";
            case "10" -> "October ";
            case "11" -> "November ";
            case "12" -> "December ";
            default -> "";
        };
    }
}
