package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.models.SteamGame;
import com.project.steamtwitchintegration.models.TwitchGame;

import java.util.ArrayList;
import java.util.List;

public class Parser {
    public List<Game> games;
    public List<SteamGame> steamGames;
    public List<TwitchGame> twitchGames;

    public void loadGames() {
        games = new ArrayList<>();
        for (SteamGame steamGame : steamGames) {
            games.stream()
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
                                games.add(game);
                            }
                    );
        }
//        wybranie gier z iloscia wpisow wieksza od 10
        games = games.stream().filter(game -> game.getGameRecords().size() > 30).toList();
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
    public void exportData(String destinationPath, Filetype filetype) {
        switch (filetype){
            case CSV -> {
//                TODO eksport danych z games do CSV
            }
            case JSON -> {
//                TODO eskport danych z games do JSON
            }
            case XML -> {
//                TODO eksport danych z games do XML
            }
        }
    }
}
