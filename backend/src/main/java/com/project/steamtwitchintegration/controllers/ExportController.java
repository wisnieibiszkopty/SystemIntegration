package com.project.steamtwitchintegration.controllers;

import com.project.steamtwitchintegration.services.ExportService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/exports")
public class ExportController {

    Map<String, MediaType> formats = Map.of(
            "json", MediaType.APPLICATION_JSON,
            "xml", MediaType.APPLICATION_XML,
            "csv", MediaType.parseMediaType("text/csv")
    );

    private final ExportService exportService;

    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/{format}")
    public ResponseEntity<ByteArrayResource> exportAllGames(@PathVariable String format){
        ByteArrayResource resource = exportService.exportAll(format);
        return ResponseEntity.ok()
            .contentType(formats.get(format))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data." + format)
            .body(resource);
    }

    @GetMapping("/{format}/{id}")
    public ResponseEntity<ByteArrayResource> exportGame(@PathVariable String format, @PathVariable Long id){
        ByteArrayResource resource = exportService.export(id, format);
        return ResponseEntity.ok()
            .contentType(formats.get(format))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=data_" + id + "." + format)
            .body(resource);
    }

}
