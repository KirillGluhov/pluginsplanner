package com.plugins.planner.controllers;

import com.plugins.planner.models.Manifest;
import com.plugins.planner.services.PluginService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Path;
import java.util.List;


@RequestMapping("/plugins")
@RequiredArgsConstructor
@RestController
public class PluginController {
    private final PluginService pluginService;

    @GetMapping("/")
    public ResponseEntity<List<String>> getPluginNames()
    {
        List<String> paths = pluginService.getPluginNames();

        return ResponseEntity.ok(paths);
    }

    @GetMapping("{pluginName}/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String pluginName, @PathVariable String fileName)
    {
        Resource file = pluginService.getFile(pluginName, fileName);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, file.getFilename().contains("png") ? "image/png" : "text/html")
                .body(file);
    }

    @GetMapping("/manifest/{pluginName}")
    public ResponseEntity<Resource> getManifest(@PathVariable String pluginName)
    {
        Resource manifest = pluginService.getManifest(pluginName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + manifest.getFilename() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(manifest);

    }
}
