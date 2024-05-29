package com.project.steamtwitchintegration.dataConvertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.steamtwitchintegration.models.Game;

import java.io.*;
import java.util.List;

public class JsonParser extends Parser implements DataParser{
    String STEAM_JSON_CONDITION = "name";
    String TWITCH_JSON_CONDITION = "title";

    public void importData(InputStream inputStream) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(new InputStreamReader(inputStream));
            if (jsonNode.isArray() && !jsonNode.isEmpty()) {
                if (jsonNode.get(0).has(STEAM_JSON_CONDITION)) {
                    setSteamGames(mapper.readValue(new InputStreamReader(inputStream), new TypeReference<>() {}));
                } else if (jsonNode.get(0).has(TWITCH_JSON_CONDITION)) {
                    setTwitchGames(mapper.readValue(new InputStreamReader(inputStream), new TypeReference<>() {}));
                } else {
                    throw new IOException("JsonParser.importData() - Unknown data format!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void exportData(OutputStream outputStream, List<Game> gamesToExport) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(new OutputStreamWriter(outputStream), gamesToExport);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
