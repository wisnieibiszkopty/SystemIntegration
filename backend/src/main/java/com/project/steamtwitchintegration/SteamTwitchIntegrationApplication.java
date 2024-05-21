package com.project.steamtwitchintegration;

import com.project.steamtwitchintegration.dataConvertion.CsvParser;
import com.project.steamtwitchintegration.dataConvertion.Filetype;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
public class SteamTwitchIntegrationApplication {

	public static void main(String[] args) {
		CsvParser csvParser = new CsvParser();
		csvParser.importData("C:\\Users\\pwins\\IdeaProjects\\SystemIntegration\\backend\\src\\main\\resources\\data\\SteamCharts.csv");
//		csvParser.exportData("src/main/resources/data/SteamTEST.csv", Filetype.CSV);
//		csvParser.exportData("src/main/resources/data/SteamTEST.json", Filetype.JSON);
//		csvParser.exportData("src/main/resources/data/SteamTEST.xml", Filetype.XML);
		csvParser.loadSteamGames();
		System.out.println(csvParser);

		csvParser.importData("C:\\Users\\pwins\\IdeaProjects\\SystemIntegration\\backend\\src\\main\\resources\\data\\Twitch_game_data.csv");
//		csvParser.exportData("src/main/resources/data/TwitchTEST.csv", Filetype.CSV);
//		csvParser.exportData("src/main/resources/data/TwitchTEST.json", Filetype.JSON);
//		csvParser.exportData("src/main/resources/data/TwitchTEST.xml", Filetype.XML);
		csvParser.loadTwitchGames();
		System.out.println(csvParser);

		csvParser.showgames();

//		SpringApplication.run(SteamTwitchIntegrationApplication.class, args);
	}

}
