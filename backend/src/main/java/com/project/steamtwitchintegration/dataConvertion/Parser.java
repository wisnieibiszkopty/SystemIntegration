package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.models.SteamGame;
import com.project.steamtwitchintegration.models.TwitchGame;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


public class Parser {
    @Getter
    @Setter
    public static List<Game> games;
    @Getter
    @Setter
    public static List<SteamGame> steamGames;
    @Getter
    @Setter
    public static List<TwitchGame> twitchGames;

    public List<Game> loadGames() {
        List<Game> temp = new ArrayList<>();
        for (SteamGame steamGame : steamGames) {
            temp.stream()
                    .filter(game1 -> game1.getGameName().equals(steamGame.getName()))
                    .findFirst()
                    .ifPresentOrElse(
                            (game1) -> {
//                        dodanie GameRecord do istniejacego obiektu
                                twitchGames.stream()
                                        .filter(twitchGame ->
                                                game1.getGameName().equals(twitchGame.getTitle())
                                                        && twitchGame.getYear().equals(steamGame.getYear())
                                                        && steamGame.getMonth().equals(twitchGame.getMonth())
                                        )
                                        .forEach(twitchGame -> game1.addGameRecord(gameRecordInitialize(game1, steamGame, twitchGame)));
                            },
                            () -> {
//                        stworzenie nowego obiektu i dodanie GameRecord
                                Game game = new Game();
                                game.setGameName(steamGame.getName());
                                twitchGames.stream()
                                        .filter(twitchGame ->
                                                game.getGameName().equals(twitchGame.getTitle())
                                                        && twitchGame.getYear().equals(steamGame.getYear())
                                                        && steamGame.getMonth().equals(twitchGame.getMonth())
                                        )
                                        .forEach(twitchGame -> game.addGameRecord(gameRecordInitialize(game, steamGame, twitchGame)));
                                temp.add(game);
                            }
                    );
        }
//        wybranie gier z iloscia wpisow wieksza od 10
        return temp.stream().filter(game -> game.getGameRecords().size() > 30).toList();
    }

    private GameRecord gameRecordInitialize(Game game, SteamGame steamGame, TwitchGame twitchGame) {
        GameRecord gameRecord = new GameRecord();
        gameRecord.setGame(game);
        gameRecord.setYear(steamGame.getYear());
        gameRecord.setMonth(steamGame.getMonth());
        gameRecord.setSteamAveragePlayers(steamGame.getAverage());
        gameRecord.setSteamGainPlayers(steamGame.getGain());
        gameRecord.setSteamPeakPlayers(steamGame.getPeak());
        gameRecord.setSteamAvgPeakPerc(steamGame.getAveragePeakPercent());
        gameRecord.setTwitchHoursWatched(twitchGame.getHoursWatched());
        gameRecord.setTwitchHoursStreamed(twitchGame.getHoursStreamed());
        gameRecord.setTwitchPeakViewers(twitchGame.getPeakViewers());
        gameRecord.setTwitchPeakChannels(twitchGame.getPeakChannels());
        gameRecord.setTwitchStreamers(twitchGame.getStreamers());
        gameRecord.setTwitchAvgViewers(twitchGame.getAverageViewers());
        gameRecord.setTwitchAvgChannels(twitchGame.getAverageChannels());
        gameRecord.setTwitchAvgViewerRatio(twitchGame.getAverageViewerRatio());
        return gameRecord;
    }
    public void showGames() {
        System.out.println("ILOSC GIER: " + games.size());
        for (Game g : games) {
            if (g.getGameRecords().isEmpty()) {
                System.out.println("\nGRA: " + g.getGameName() + " - nie posiada żadnych danych z Twitch'a!");
            } else {
                System.out.println("\n\tGRA:" + g.getGameName());
                System.out.println("Ilość wpisów z Twitcha: " + g.getGameRecords().size());
                for (GameRecord gameRecord : g.getGameRecords()){
                    System.out.println("DATA: " + gameRecord.getYear() + " - " + gameRecord.getMonth() + ": Srednia Widzow " + gameRecord.getTwitchAvgViewers() + ", Srednia graczy " + gameRecord.getSteamAveragePlayers());
                }
            }
        }
    }
}
