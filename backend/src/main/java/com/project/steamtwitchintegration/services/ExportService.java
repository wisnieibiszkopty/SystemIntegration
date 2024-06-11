package com.project.steamtwitchintegration.services;

import com.project.steamtwitchintegration.dataConvertion.*;
import com.project.steamtwitchintegration.models.Game;
import com.project.steamtwitchintegration.repositories.GameRepository;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;

@Service
public class ExportService {

    private final GameRepository gameRepository;

    public ExportService(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Transactional(
        propagation = Propagation.REQUIRED,
        timeout = 15,
        readOnly = true)
    public ByteArrayResource export(Long id, String format){
        List<Game> game = gameRepository.findAllById(Collections.singleton(id));
        return exportToFormat(game, format);
    }

    @Transactional(
        propagation = Propagation.REQUIRED,
        timeout = 15,
        readOnly = true)
    public ByteArrayResource exportAll(String format){
        List<Game> games = gameRepository.findAll();
        return exportToFormat(games, format);
    }

    private ByteArrayResource exportToFormat(List<Game> games, String format){
        DataParser parser;
        switch (format){
            case "xml" -> parser = new XmlParser();
            case "json" -> parser = new JsonParser();
            default -> parser = new CsvParser();
        }

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        parser.exportData(output, games);
        byte[] data = output.toByteArray();
        return new ByteArrayResource(data);
    }
}
