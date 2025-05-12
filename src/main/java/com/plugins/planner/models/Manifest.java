package com.plugins.planner.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Manifest
{
    private String id;
    private String name;
    private String version;
    private List<String> files;
    private MenuItem menuItem;
    private String description;
    private String entry;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuItem
    {
        private String icon;
        private String text;
        private String path;
    }
}
