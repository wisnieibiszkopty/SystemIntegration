package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.GameRecord;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO dokonczyc
public class XmlParser extends Parser implements DataParser{
    @Override
    public void importData(String sourcePath) {

    }
    @Override
    public void exportData(String destinationPath) {
        XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
        try {
            XMLStreamWriter writer = outputFactory.createXMLStreamWriter(new FileOutputStream(destinationPath), "UTF-8");
            writer.writeStartDocument("UTF-8", "1.0");
            writer.writeStartElement("GamesData");
            for (Game game : getGames()){
                writer.writeStartElement("Game");
                writer.writeStartElement("gamename");
                writer.writeCharacters(game.getGameName());
                writer.writeEndElement();
                writer.writeStartElement("id");
//                TODO wartości id są puste, jak sie bedzie brało z bazy danych powinno byc juz git
                writer.writeCharacters(Long.toString(0));
                writer.writeEndElement();
                for (GameRecord gameRecord : game.getGameRecords()){
                    writer.writeStartElement("GameRecord");
                    writer.writeStartElement("year");
                    writer.writeCharacters(gameRecord.getYear());
                    writer.writeEndElement();

                    writer.writeStartElement("month");
                    writer.writeCharacters(gameRecord.getMonth());
                    writer.writeEndElement();

                    writer.writeStartElement("steamAveragePlayers");
                    writer.writeCharacters(Double.toString(gameRecord.getSteamAveragePlayers()));
                    writer.writeEndElement();

                    writer.writeStartElement("steamGainPlayers");
                    writer.writeCharacters(Double.toString(gameRecord.getSteamGainPlayers()));
                    writer.writeEndElement();

                    writer.writeStartElement("steamPeakPlayers");
                    writer.writeCharacters(Integer.toString(gameRecord.getSteamPeakPlayers()));
                    writer.writeEndElement();

                    writer.writeStartElement("steamAvgPeakPerc");
                    writer.writeCharacters(gameRecord.getSteamAvgPeakPerc());
                    writer.writeEndElement();

// Dane z Twitcha
                    writer.writeStartElement("twitchHoursWatched");
                    writer.writeCharacters(Integer.toString(gameRecord.getTwitchHoursWatched()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchHoursStreamed");
                    writer.writeCharacters(Integer.toString(gameRecord.getTwitchHoursStreamed()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchPeakViewers");
                    writer.writeCharacters(Integer.toString(gameRecord.getTwitchPeakViewers()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchPeakChannels");
                    writer.writeCharacters(Integer.toString(gameRecord.getTwitchPeakChannels()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchStreamers");
                    writer.writeCharacters(Integer.toString(gameRecord.getTwitchStreamers()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchAvgViewers");
                    writer.writeCharacters(Integer.toString(gameRecord.getTwitchAvgViewers()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchAvgChannels");
                    writer.writeCharacters(Integer.toString(gameRecord.getTwitchAvgChannels()));
                    writer.writeEndElement();

                    writer.writeStartElement("twitchAvgViewerRatio");
                    writer.writeCharacters(Double.toString(gameRecord.getTwitchAvgViewerRatio()));
                    writer.writeEndElement();
                    writer.writeEndElement();

//                    Field[] fields = GameRecord.class.getDeclaredFields();
//                    for (Field field : fields) {
//                        field.setAccessible(true);
//                        writer.writeStartElement(field.getName());
////                        TODO String.valueOf() nie dziala, trzeba to porzadniej zrobic
//                        writer.writeCharacters("");
//                        writer.writeEndElement();
//                    }
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
