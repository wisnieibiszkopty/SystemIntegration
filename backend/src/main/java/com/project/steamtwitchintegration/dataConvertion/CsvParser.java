package com.project.steamtwitchintegration.dataConvertion;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.project.steamtwitchintegration.models.*;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.*;

public class CsvParser extends Parser implements DataParser {
    String STEAM_CSV_CONDITION = "gamename";
    String TWITCH_CSV_CONDITION = "Rank";
    public List<String[]> csv;
    public String[] csvFirstRow;
    @Override
    public void importData(InputStream inputStream) {
        this.csv = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new InputStreamReader(inputStream))) {
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
            System.out.println("CsvParser.importData() error");
        }
    }

    @Override
    public void exportData(OutputStream outputStream, List<Game> gamesToExport) {
        String[] headers = {
                "game",
                "year",
                "month",
                "steamAvergePlayers",
                "steamGainPlayers",
                "steamPeakPlayers",
                "steamAvgPeakPerc",
                "twitchHoursWatched",
                "twitchHoursStreamed",
                "twitchPeakViewers",
                "twitchPeakChannels",
                "twitchStreamers",
                "twitchAvgViewers",
                "twitchAvgChannels",
                "twitchAvgViewerRatio"
        };
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(outputStream))) {
            writer.writeNext(headers);
            List<String[]> csv = new ArrayList<>();
            for (Game game : gamesToExport) {
                for (GameRecord gameRecord : game.getGameRecords()) {
                    SteamStats steamStats = gameRecord.getSteamStats();
                    TwitchStats twitchStats = gameRecord.getTwitchStats();
                    String[] csvGameRecord = {

                            game.getGameName(),
                            gameRecord.getYear(),
                            gameRecord.getMonth(),

                            Double.toString(steamStats.getSteamAveragePlayers()),
                            Double.toString(steamStats.getSteamGainPlayers()),
                            Integer.toString(steamStats.getSteamPeakPlayers()),
                            steamStats.getSteamAvgPeakPerc(),

                            Integer.toString(twitchStats.getTwitchHoursWatched()),
                            Integer.toString(twitchStats.getTwitchHoursStreamed()),
                            Integer.toString(twitchStats.getTwitchPeakViewers()),
                            Integer.toString(twitchStats.getTwitchPeakChannels()),
                            Integer.toString(twitchStats.getTwitchStreamers()),
                            Integer.toString(twitchStats.getTwitchAvgViewers()),
                            Integer.toString(twitchStats.getTwitchAvgChannels()),
                            Double.toString(twitchStats.getTwitchAvgViewerRatio()),
                    };
                    csv.add(csvGameRecord);
                }
            }
            writer.writeAll(csv);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public void loadSteamGames(){
        setSteamGames(new ArrayList<>());
        for (String[] s : csv) {
            if (s.length == 7){
                SteamGame game = new SteamGame();
                game.setName(s[0]);
                game.setYear(s[1]);
                //game.setMonth(s[2]);
                game.setMonth(monthConverterToNumber(s[2]));
                game.setAverage(Double.parseDouble(s[3]));
                game.setGain(Double.parseDouble(s[4]));
                game.setPeak(Integer.parseInt(s[5]));
                game.setAveragePeakPercent(s[6]);
                steamGames.add(game);
//                addGameByName(game.getName());
            }
        }
    }
    public void loadTwitchGames(){
        setTwitchGames(new ArrayList<>());
        for (String[] s : csv){
            if (s.length == 12) {
                TwitchGame game = new TwitchGame();
                game.setTitle(s[1]);
                // instead of mapping numeric months to words inverting it,
                // so it can be easier drawn on plot
                //game.setMonth(monthConvert(s[2]));
                game.setMonth(s[2]);
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
//                addGameByName(game.getTitle());
            }
        }
    }


}
