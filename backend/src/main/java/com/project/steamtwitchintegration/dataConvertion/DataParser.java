package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.models.SteamGame;
import com.project.steamtwitchintegration.models.TwitchGame;

import java.util.List;

public interface DataParser {
    /**
     * Funkcja wczytująca dane z pliku
     * @param sourcePath ścieżka odczytu
     */
    void importData(String sourcePath);

    /**
     * Funkcja zapisujaca dane do pliku
     * @param destinationPath ścieżka zapisu
     * @param filetype format zapisu
     */
    void exportData(String destinationPath, Filetype filetype);
}
