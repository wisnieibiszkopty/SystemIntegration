package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;
import com.project.steamtwitchintegration.models.SteamStats;
import com.project.steamtwitchintegration.models.TwitchStats;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class XmlParser extends Parser implements DataParser{

    @Override
    public void importData(String sourcePath) {

    }
    @Override
    public void exportData(String destinationPath, List<Game> gamesToExport) {
        gamesToExport = gamesToExport.subList(0,5);
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        try {
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileOutputStream(destinationPath), "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("GamesData");
            for (Game game : gamesToExport){
                writer.writeStartElement("Game");
                writer.writeStartElement("gamename");
                writer.writeCharacters(game.getGameName());
                writer.writeEndElement();
                writer.writeStartElement("gameId");
                writer.writeCharacters(Long.toString(game.getId()));
                writer.writeEndElement();
                for (GameRecord gameRecord : game.getGameRecords()){
                    SteamStats steamStats = gameRecord.getSteamStats();
                    TwitchStats twitchStats = gameRecord.getTwitchStats();

                    writer.writeStartElement("GameRecord");

                    writer.writeStartElement("recordId");
                    writer.writeCharacters(Long.toString(gameRecord.getId()));
                    writer.writeEndElement();

                    writer.writeStartElement("year");
                    writer.writeCharacters(gameRecord.getYear());
                    writer.writeEndElement();

                    writer.writeStartElement("month");
                    writer.writeCharacters(gameRecord.getMonth());
                    writer.writeEndElement();

                    writer.writeStartElement("steamAveragePlayers");
                    writer.writeCharacters(Double.toString(steamStats.getSteamAveragePlayers()));
                    writer.writeEndElement();

                    writer.writeStartElement("steamGainPlayers");
                    writer.writeCharacters(Double.toString(steamStats.getSteamGainPlayers()));
                    writer.writeEndElement();

                    writer.writeStartElement("steamPeakPlayers");
                    writer.writeCharacters(Integer.toString(steamStats.getSteamPeakPlayers()));
                    writer.writeEndElement();

                    writer.writeStartElement("steamAvgPeakPerc");
                    writer.writeCharacters(steamStats.getSteamAvgPeakPerc());
                    writer.writeEndElement();

// Dane z Twitcha
                    writer.writeStartElement("twitchHoursWatched");
                    writer.writeCharacters(Integer.toString(twitchStats.getTwitchHoursWatched()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchHoursStreamed");
                    writer.writeCharacters(Integer.toString(twitchStats.getTwitchHoursStreamed()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchPeakViewers");
                    writer.writeCharacters(Integer.toString(twitchStats.getTwitchPeakViewers()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchPeakChannels");
                    writer.writeCharacters(Integer.toString(twitchStats.getTwitchPeakChannels()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchStreamers");
                    writer.writeCharacters(Integer.toString(twitchStats.getTwitchStreamers()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchAvgViewers");
                    writer.writeCharacters(Integer.toString(twitchStats.getTwitchAvgViewers()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchAvgChannels");
                    writer.writeCharacters(Integer.toString(twitchStats.getTwitchAvgChannels()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchAvgViewerRatio");
                    writer.writeCharacters(Double.toString(twitchStats.getTwitchAvgViewerRatio()));
                    writer.writeEndElement();
                    writer.writeEndElement();
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
