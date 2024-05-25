package com.project.steamtwitchintegration.dataConvertion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.models.SteamGame;
import com.project.steamtwitchintegration.models.TwitchGame;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.util.*;

@Getter
@Setter
@Slf4j
public class CsvParser extends Parser implements DataParser {
    String STEAM_CSV_CONDITION = "gamename";
    String TWITCH_CSV_CONDITION = "Rank";

    public List<String[]> csv;
    public String[] csvFirstRow;

    @Override
    public void importData(String sourcePath) {
        this.csv = new ArrayList<>();
        try (CSVReader reader = new CSVReader(new FileReader(sourcePath))) {
            this.csv = reader.readAll();
        } catch (IOException | CsvException e) {
            throw new RuntimeException(e);
        }
//        odcina pierwszy wiersz z nagłówkiem
        csvFirstRow = this.csv.get(0);
//        ucina pierwszy wiersz ( z nagłówkami ) i bierze tylko do  n-tego ( do testów aby mniej mieliło )
//        this.csv = csv.subList(1,50);
        this.csv.remove(0);
        if (csvFirstRow[0].equals(STEAM_CSV_CONDITION)) {
            steamGames = new ArrayList<>();
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
                    steamGames.add(game);
                }
            }
        } else if (csvFirstRow[0].equals(TWITCH_CSV_CONDITION)) {
            twitchGames = new ArrayList<>();
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
                }
            }
        } else {
            log.error("CsvParser.importData()");
        }
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
