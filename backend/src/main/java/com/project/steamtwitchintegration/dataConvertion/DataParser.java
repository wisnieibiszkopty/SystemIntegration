package com.project.steamtwitchintegration.dataConvertion;

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

    /**
     * Funkcja wczytujaca zaimportowane dane o grach ze Steama
     */
    void loadSteamGames();

    /**
     * Funkcja wczytujaca zaimportowane dane o grach z Twitcha
     */
    void loadTwitchGames();
}
