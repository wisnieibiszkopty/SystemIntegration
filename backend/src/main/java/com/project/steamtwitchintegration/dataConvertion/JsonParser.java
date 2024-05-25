package com.project.steamtwitchintegration.dataConvertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.SteamGame;
import com.project.steamtwitchintegration.models.TwitchGame;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParser extends Parser implements DataParser{

    String STEAM_JSON_CONDITION = "name";
    String TWITCH_JSON_CONDITION = "title";

    private List<SteamGame> steamGames;
    private List<TwitchGame> twitchGames;

    public void importData(String sourcePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(new File(sourcePath));
            if (jsonNode.isArray() && !jsonNode.isEmpty()) {
                if (jsonNode.get(0).has(STEAM_JSON_CONDITION)) {
                    this.steamGames = new ArrayList<>();
                    this.steamGames = mapper.readValue(new File(sourcePath), new TypeReference<>() {});
                    setSteamGames(this.steamGames);
                } else if (jsonNode.get(0).has(TWITCH_JSON_CONDITION)) {
                    this.twitchGames = new ArrayList<>();
                    this.twitchGames = mapper.readValue(new File(sourcePath), new TypeReference<>() {});
                    setTwitchGames(this.twitchGames);
                } else {
                    throw new IOException("JsonParser.importData() - Unknown data format!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setupData(){
        setGames(super.loadGames());
    }
    public void exportData(String destinationPath) {
//        TODO poprawic zapis do json
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new File(destinationPath), getGames());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
