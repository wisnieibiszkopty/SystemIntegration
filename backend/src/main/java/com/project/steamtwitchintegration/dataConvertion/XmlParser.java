package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class XmlParser extends Parser implements DataParser{
    String STEAM_XML_CONDITION = "SteamGamesStats";
    String TWITCH_XML_CONDITION = "TwitchGamesStats";

    @Override
    public void importData(String sourcePath) {
        try {
            File xmlToImport = new File(sourcePath);
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(xmlToImport);
//            document.getDocumentElement().normalize();


            // check what type of data needs to be imported
            Element rootElement = document.getDocumentElement();

            if (rootElement.getNodeName().equals(STEAM_XML_CONDITION)) {
                NodeList gameList = document.getElementsByTagName("Game");
                setSteamGames(new ArrayList<>());
                for (int i=0; i<gameList.getLength(); i++){
                    Node gameNode = gameList.item(i);
                    if (gameNode.getNodeType() == Node.ELEMENT_NODE){
                        Element gameElement = (Element) gameNode;
                        SteamGame steamGame = new SteamGame();
                        steamGame.setName(gameElement.getElementsByTagName("gamename").item(0).getTextContent());
                        steamGame.setYear(gameElement.getElementsByTagName("year").item(0).getTextContent());
                        steamGame.setMonth(gameElement.getElementsByTagName("month").item(0).getTextContent());
                        steamGame.setAverage(Double.parseDouble(gameElement.getElementsByTagName("avg").item(0).getTextContent()));
                        steamGame.setGain(Double.parseDouble(gameElement.getElementsByTagName("gain").item(0).getTextContent()));
                        steamGame.setPeak(Integer.parseInt(gameElement.getElementsByTagName("peak").item(0).getTextContent()));
                        steamGame.setAveragePeakPercent(gameElement.getElementsByTagName("avg_peak_perc").item(0).getTextContent());
                        System.out.println(steamGame.getName());
                        steamGames.add(steamGame);
                    }
                }
            } else if (rootElement.getNodeName().equals(TWITCH_XML_CONDITION)) {
                NodeList gameList = document.getElementsByTagName("TwitchGame");
                setTwitchGames(new ArrayList<>());
                for (int i=0; i<gameList.getLength(); i++) {
                    Node gameNode = gameList.item(i);
                    if (gameNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element gameElement = (Element) gameNode;
                        TwitchGame twitchGame = new TwitchGame();
                        twitchGame.setTitle(gameElement.getElementsByTagName("Game").item(0).getTextContent());
                        twitchGame.setMonth(gameElement.getElementsByTagName("Month").item(0).getTextContent());
                        twitchGame.setYear(gameElement.getElementsByTagName("Year").item(0).getTextContent());
                        twitchGame.setHoursWatched(Integer.parseInt(gameElement.getElementsByTagName("Hours_watched").item(0).getTextContent()));
                        twitchGame.setHoursStreamed(Integer.parseInt(gameElement.getElementsByTagName("Hours_streamed").item(0).getTextContent()));
                        twitchGame.setPeakViewers(Integer.parseInt(gameElement.getElementsByTagName("Peak_viewers").item(0).getTextContent()));
                        twitchGame.setPeakChannels(Integer.parseInt(gameElement.getElementsByTagName("Peak_channels").item(0).getTextContent()));
                        twitchGame.setStreamers(Integer.parseInt(gameElement.getElementsByTagName("Streamers").item(0).getTextContent()));
                        twitchGame.setAverageViewers(Integer.parseInt(gameElement.getElementsByTagName("Avg_viewers").item(0).getTextContent()));
                        twitchGame.setAverageChannels(Integer.parseInt(gameElement.getElementsByTagName("Avg_channels").item(0).getTextContent()));
                        twitchGame.setAverageViewerRatio(Double.parseDouble(gameElement.getElementsByTagName("Avg_viewer_ratio").item(0).getTextContent()));
                        System.out.println(twitchGame.getTitle());
                        twitchGames.add(twitchGame);
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }
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
