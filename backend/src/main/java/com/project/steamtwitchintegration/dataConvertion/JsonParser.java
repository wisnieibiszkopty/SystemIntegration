package com.project.steamtwitchintegration.dataConvertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.repositories.GameRepository;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonParser extends Parser implements DataParser{
    String STEAM_JSON_CONDITION = "name";
    String TWITCH_JSON_CONDITION = "title";

    public void importData(String sourcePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(new File(sourcePath));
            if (jsonNode.isArray() && !jsonNode.isEmpty()) {
                if (jsonNode.get(0).has(STEAM_JSON_CONDITION)) {
                    setSteamGames(mapper.readValue(new File(sourcePath), new TypeReference<>() {}));
                } else if (jsonNode.get(0).has(TWITCH_JSON_CONDITION)) {
                    setTwitchGames(mapper.readValue(new File(sourcePath), new TypeReference<>() {}));
                } else {
                    throw new IOException("JsonParser.importData() - Unknown data format!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void exportData(String destinationPath, List<Game> gamesToExport) {
        ObjectMapper mapper = new ObjectMapper();
        gamesToExport = gamesToExport.subList(0,5);
        try {
            mapper.writeValue(new File(destinationPath), gamesToExport);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
