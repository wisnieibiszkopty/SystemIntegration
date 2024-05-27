package com.project.steamtwitchintegration.dataConvertion;

import com.project.steamtwitchintegration.models.Game;

import java.util.List;

public interface DataParser {
    /**
     * Function importing data from file
     * @param sourcePath file to read path
     */
    void importData(String sourcePath);

    /**
     * Function saving Game objects to file
     * @param destinationPath file to write path
     * @param gamesToExport List of Game objects to save
     */
    void exportData(String destinationPath, List<Game> gamesToExport);
}
