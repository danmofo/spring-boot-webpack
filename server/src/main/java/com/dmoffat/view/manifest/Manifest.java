package com.dmoffat.view.manifest;

import java.util.Map;

/**
 * Represents a manfiest.json file. This file is created by the front-end build process, it contains all
 * entry points (CSS and JS files) along with their hashed file name.
 *
 * @author danielmoffat
 */
public class Manifest {
    Map<String, String> fileMap;

    public Manifest(Map<String, String> fileMap) {
        this.fileMap = fileMap;
    }

    public String getForPath(String filename) {
        // In the output, all entry points begin with a "/"
        return fileMap.getOrDefault("/" + filename, null);
    }
}
