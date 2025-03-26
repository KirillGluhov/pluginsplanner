package com.plugins.planner.services.impls;

import com.plugins.planner.services.PluginService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PluginServiceImpl implements PluginService {
    private final Path pluginStoragePath = Paths.get("/opt/plugins");

    @Override
    public Resource getManifest(String name) {
        Path filePath = pluginStoragePath.resolve(name + "/manifest.json");

        if (!Files.exists(filePath)) {
            return getDefaultManifest();
        }

        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            return getDefaultManifest();
        }
    }

    @Override
    public Resource getFile(String pluginName, String fileName) {
        Path filePath = pluginStoragePath.resolve(pluginName + "/" + fileName);

        if (!Files.exists(filePath))
        {
            return null;
        }

        try {
            return new UrlResource(filePath.toUri());
        } catch (MalformedURLException e) {
            return null;
        }
    }

    private Resource getDefaultManifest()
    {
        String defaultJson = "{}";
        return new ByteArrayResource(defaultJson.getBytes());
    }

    @Override
    public List<String> getPluginNames() {
        try (var paths = Files.list(pluginStoragePath)) {
            return paths.filter(Files::isDirectory)
                    .map(path -> pluginStoragePath.relativize(path).toString())
                    .collect(Collectors.toList());
        }
        catch (Exception e)
        {
            return List.of();
        }
    }
}
