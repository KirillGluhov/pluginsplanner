package com.plugins.planner.services;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;


public interface PluginService
{
    Resource getManifest(String name);

    Resource getFile(String pluginName, String fileName);

    List<String> getPluginNames();
}
