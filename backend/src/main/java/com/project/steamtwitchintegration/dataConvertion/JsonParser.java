package com.project.steamtwitchintegration.dataConvertion;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JsonParser implements DataParser{
    private Map<?,?> json;
    private ObjectMapper mapper = new ObjectMapper();
    @Override
    public void importData(String sourcePath) {
        this.json = new HashMap<>();
        try {
            this.json = mapper.readValue(new File(sourcePath), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void exportData(String destinationPath, Filetype filetype) {
        try {
            mapper.writeValue(new File(destinationPath), json);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void loadSteamGames() {

    }

    @Override
    public void loadTwitchGames() {

    }

    @Override
    public String toString() {
        String jsonString = "";
        try {
            jsonString = mapper.writeValueAsString(json);
            System.out.println(jsonString);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
