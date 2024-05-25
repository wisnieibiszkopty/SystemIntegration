package com.project.steamtwitchintegration.dataConvertion;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

// TODO dokonczyc
public class JsonParser extends Parser implements DataParser{

    String STEAM_JSON_CONDITION = "name";
    String TWITCH_JSON_CONDITION = "title";

    @Override
    public void importData(String sourcePath) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(new File(sourcePath));
            if (jsonNode.isArray() && !jsonNode.isEmpty()) {
                if (jsonNode.get(0).has(STEAM_JSON_CONDITION)) {
                    super.steamGames = new ArrayList<>();
                    super.steamGames = mapper.readValue(new File(sourcePath), new TypeReference<>() {});
                } else if (jsonNode.get(0).has(TWITCH_JSON_CONDITION)) {
                    super.twitchGames = new ArrayList<>();
                    super.twitchGames = mapper.readValue(new File(sourcePath), new TypeReference<>() {});
                } else {
                    throw new IOException("JsonParser.importData() - Unknown data format!");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportData(String destinationPath, Filetype filetype) {
    }
}
