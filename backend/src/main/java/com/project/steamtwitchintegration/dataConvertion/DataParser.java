package com.project.steamtwitchintegration.dataConvertion;

public interface DataParser {
    void importData(String sourcePath);
    void exportData(String destinationPath);
}
