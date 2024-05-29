package com.project.steamtwitchintegration.dataConvertion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.project.steamtwitchintegration.models.*;
import com.project.steamtwitchintegration.repositories.GameRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.*;

@Getter
@Setter
@Slf4j
@Service
public class CsvParser implements DataParser {
    public List<Game> games = new ArrayList<>();

    String STEAM_CSV_CONDITION = "gamename";
    String TWITCH_CSV_CONDITION = "Rank";

    public List<String[]> csv;
    public String[] csvFirstRow;
    public List<SteamGame> steamGames;
    public List<TwitchGame> twitchGames;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void importData(String sourcePath) {
        this.games = new ArrayList<>();
        this.csv = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(sourcePath))) {
            this.csv = reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
//        odcina pierwszy wiersz z nagłówkiem
        csvFirstRow = this.csv.get(0);
//        ucina pierwszy wiersz ( z nagłówkami ) i bierze tylko do  n-tego ( do testów aby mniej mieliło )
        this.csv.remove(0);
        if (csvFirstRow[0].equals(STEAM_CSV_CONDITION)) {
            loadSteamGames();
        } else if (csvFirstRow[0].equals(TWITCH_CSV_CONDITION)) {
            loadTwitchGames();
        } else {
            log.error("CsvParser.importData()");
        }
    }

    private void addGameByName(String name){
        if(games.stream().anyMatch(game -> game.getGameName().equals(name))){
            //log.info("Game already exists");
            return;
        }

        Game game = new Game();
        game.setGameName(name);
        games.add(game);
        log.info("Added: " + game.getGameName());
    }

    @Override
    public void exportData(String destinationPath, Filetype filetype) {

        switch (filetype){
            case CSV -> {
                try (CSVWriter writer = new CSVWriter(new FileWriter(destinationPath))) {
                    writer.writeNext(csvFirstRow);
                    writer.writeAll(this.csv);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case JSON -> {
                ObjectMapper mapper = new ObjectMapper();
                try {

                    if (csvFirstRow[0].equals(STEAM_CSV_CONDITION)){
                        mapper.writeValue(new File(destinationPath), steamGames);
                    } else if (csvFirstRow[0].equals(TWITCH_CSV_CONDITION)) {
                        mapper.writeValue(new File(destinationPath), twitchGames);
                    }

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case XML -> {
                XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
                String objectName = (csvFirstRow[0].equals(STEAM_CSV_CONDITION)) ? "SteamGamesStats" : "TwitchGamesStats";
                try {
                    XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileOutputStream(destinationPath), "UTF-8");
                    ArrayList<String> localNames = new ArrayList<>(Arrays.asList(csvFirstRow));

                    writer.writeStartDocument("UTF-8", "1.0");
                    writer.writeStartElement(objectName);
                    for (String[] s : csv){
                        writer.writeStartElement("Game");
                        int i = 0;
                        for (String temp : s){
                            writer.writeStartElement(localNames.get(i));
                            writer.writeCharacters(temp);
                            writer.writeEndElement();
                            i++;
                        }
                        writer.writeEndElement();
                    }
                    writer.writeEndElement();
                    writer.writeEndDocument();
                    writer.flush();
                    writer.close();

                } catch (XMLStreamException | FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

//  private GameRecord gameRecordInitialize(Game game, SteamGame steamGame, TwitchGame twitchGame) {
//        GameRecord gameRecord = new GameRecord();
//        gameRecord.setGame(game);
//        gameRecord.setYear(steamGame.getYear());
//        gameRecord.setMonth(steamGame.getMonth());
//        gameRecord.setSteamAveragePlayers(steamGame.getAverage());
//        gameRecord.setSteamGainPlayers(steamGame.getGain());
//        gameRecord.setSteamPeakPlayers(steamGame.getPeak());
//        gameRecord.setSteamAvgPeakPerc(steamGame.getAveragePeakPercent());
//        gameRecord.setTwitchHoursWatched(twitchGame.getHoursWatched());
//        gameRecord.setTwitchHoursStreamed(twitchGame.getHoursStreamed());
//        gameRecord.setTwitchPeakViewers(twitchGame.getPeakViewers());
//        gameRecord.setTwitchPeakChannels(twitchGame.getPeakChannels());
//        gameRecord.setTwitchStreamers(twitchGame.getStreamers());
//        gameRecord.setTwitchAvgViewers(twitchGame.getAverageViewers());
//        gameRecord.setTwitchAvgChannels(twitchGame.getAverageChannels());
//        gameRecord.setTwitchAvgViewerRatio(twitchGame.getAverageViewerRatio());
//        return gameRecord;
//      return null;
//    }

    @Override
    public void loadGames() {
        log.info("Steam games count: " + steamGames.size());
        log.info("Twitch games count: " + twitchGames.size());
        for (SteamGame steamGame : steamGames) {
            games.stream()
                    .filter(game1 -> game1.getGameName().equals(steamGame.getName()))
                    .findFirst()
                    .ifPresentOrElse(
                    (game1) -> {
//                        dodanie GameRecord do istniejacego obiektu
//                        twitchGames.stream()
//                                .filter(twitchGame ->
//                                    game1.getGameName().equals(twitchGame.getTitle())
//                                    && twitchGame.getYear().equals(steamGame.getYear())
//                                    && steamGame.getMonth().equals(twitchGame.getMonth())
//                                )
//                                .forEach(twitchGame -> game1.addGameRecord(gameRecordInitialize(game1, steamGame, twitchGame)));
                    },
                    () -> {
//                        stworzenie nowego obiektu i dodanie GameRecord
                        Game game = new Game();
                        game.setGameName(steamGame.getName());
                        // testing without records
                        twitchGames.stream()
                            .filter(twitchGame ->
                                game.getGameName().equals(twitchGame.getTitle())
                                    && twitchGame.getYear().equals(steamGame.getYear())
                                    && steamGame.getMonth().equals(twitchGame.getMonth())
                            )
                            .forEach(twitchGame -> {
//                                GameRecord record = gameRecordInitialize(game, steamGame, twitchGame);
//                                System.out.println(record);
//                                game.addGameRecord(record);
                            });
                        games.add(game);
                    }
            );
        }

//        for (Game g : games) {
//            if (g.getGameRecords().isEmpty()) {
//                System.out.println("\nGRA: " + g.getGameName() + " - nie posiada żadnych danych z Twitch'a!");
//            } else {
//                System.out.println("\n\tGRA:" + g.getGameName());
//                System.out.println("Ilość wpisów z Twitcha: " + g.getGameRecords().size());
//                for (GameRecord gameRecord : g.getGameRecords()){
//                    System.out.println("DATA: " + gameRecord.getYear() + " - " + gameRecord.getMonth() + ": Srednia Widzow " + gameRecord.getTwitchAvgViewers() + ", Srednia graczy " + gameRecord.getSteamAveragePlayers());
//                }
//            }
//        }
    }

    public TwitchStats getTwitchStats(TwitchGame twitchGame){
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

    public SteamStats getSteamStats(SteamGame steamGame){
        return SteamStats
                .builder()
                .steamAveragePlayers(steamGame.getAverage())
                .steamPeakPlayers(steamGame.getPeak())
                .steamAvgPeakPerc(steamGame.getAveragePeakPercent())
                .steamGainPlayers(steamGame.getGain())
                .build();
    }

    public void loadGames3(){
        log.info("Deleting redundant positions");
        // check if game with this name exist both in steam and twitch data
        // if not delete it
        games = games.stream().filter(game ->
            steamGames.stream()
                .anyMatch(steamGame -> steamGame
                    .getName()
                    .equals(game.getGameName()))
            && twitchGames.stream()
                .anyMatch(twitchGame -> twitchGame
                    .getTitle()
                    .equals(game.getGameName()))
        ).toList();
        log.info("Deleted games");

        gameRepository.saveAll(games);
        games = gameRepository.findAll();

        log.info("Started adding game records");
        // Reading records based on steam records first
        steamGames.forEach(game -> {
            GameRecord record = new GameRecord();
            record.setYear(game.getYear());
            record.setMonth(game.getMonth());

            games.stream()
                    .filter(game1 -> game1.getGameName().equals(game.getName()))
                    .findFirst()
                    .ifPresent(game1 -> {
                        record.setGame(game1);
                        SteamStats steamStats = getSteamStats(game);
                        steamStats.setRecord(record);
                        record.setSteamStats(steamStats);
                        game1.getGameRecords().add(record);
                    });
        });

        // next adding records from twitch
        // because record can already exist with only
        // steam data firstly searching for matching record
        // if record doesn't exist creating new one
        twitchGames.forEach(twitchGame -> {
            games.stream()
                .filter(game -> game.getGameName().equals(twitchGame.getTitle()))
                .findFirst()
                .ifPresent((existingGame -> {
                    // existingGame is game with same name as actual record to insert
                    existingGame.getGameRecords().stream()
                        .filter(game ->
                            game.getYear().equals(twitchGame.getYear()) &&
                                    game.getMonth().equals(twitchGame.getMonth())
                        )
                        .findFirst()
                        .ifPresentOrElse(
                            // gameRecord is existing record with steam data already inserted
                            // now we only have to add twitch data
                            gameRecord -> {
                                TwitchStats twitchStats = getTwitchStats(twitchGame);
                                twitchStats.setRecord(gameRecord);
                                gameRecord.setTwitchStats(twitchStats);
                                gameRecord.setGame(existingGame);
                            },
                            // gameRecord doesn't exist, adding one
                            // and filling it with twitch stats
                            () -> {
                                GameRecord record = new GameRecord();
                                record.setYear(twitchGame.getYear());
                                record.setMonth(twitchGame.getMonth());
                                TwitchStats twitchStats = getTwitchStats(twitchGame);
                                twitchStats.setRecord(record);
                                record.setTwitchStats(twitchStats);
                                record.setGame(existingGame);
                                existingGame.getGameRecords().add(record);
                            }
                        );
                }));
        });

        gameRepository.saveAll(games);
        log.info("Finished adding game records");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String s : csvFirstRow){
            sb.append(s).append("\t");
        }
        sb.append("\n");
        for (String[] row : csv){
            sb.append(String.join("\t",row)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public void loadSteamGames(){
        this.steamGames = new ArrayList<>();
        for (String[] s : csv) {
            if (s.length == 7){
                SteamGame game = new SteamGame();
                game.setName(s[0]);
                game.setYear(s[1]);
                game.setMonth(s[2]);
                game.setAverage(Double.parseDouble(s[3]));
                game.setGain(Double.parseDouble(s[4]));
                game.setPeak(Integer.parseInt(s[5]));
                game.setAveragePeakPercent(s[6]);
                this.steamGames.add(game);
                addGameByName(game.getName());
            }
        }
    }
    @Override
    public void loadTwitchGames(){
        this.twitchGames = new ArrayList<>();
        for (String[] s : csv){
            if (s.length == 12) {
                TwitchGame game = new TwitchGame();
                game.setTitle(s[1]);
                game.setMonth(monthConvert(s[2]));
                game.setYear(s[3]);
                game.setHoursWatched(Integer.parseInt(s[4]));
                game.setHoursStreamed(Integer.parseInt(s[5]));
                game.setPeakViewers(Integer.parseInt(s[6]));
                game.setPeakChannels(Integer.parseInt(s[7]));
                game.setStreamers(Integer.parseInt(s[8]));
                game.setAverageViewers(Integer.parseInt(s[9]));
                game.setAverageChannels(Integer.parseInt(s[10]));
                game.setAverageViewerRatio(Double.parseDouble(s[11]));
                twitchGames.add(game);
                addGameByName(game.getTitle());
            }
        }
    }

    /**
     * Funkcja konwertujaca dane z Twitch.csv o miesiacu w postacji "01" na "January "
     */
    private String monthConvert(String month) {
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
            case "10" -> "October";
            case "11" -> "November ";
            case "12" -> "December ";
            default -> "";
        };
    }
}
