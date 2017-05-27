package com.dmoffat.view.manifest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.HashMap;

/**
 * Loads a Manifest object into memory.
 *
 * Once constructed, you can access the manifest object through getManifest, which has a method to fetch an asset by key.
 * This lets us reference assets in our templates, without having to update the paths each time the hash changes.
 *
 * @author danielmoffat
 */

public class ManifestFileLoader {
    private static final Logger logger = LogManager.getLogger(ManifestFileLoader.class);

    private Manifest manifest;
    private Resource manifestFile;
    private ObjectMapper objectMapper;
    private boolean loadOnAccess;

    public ManifestFileLoader(Resource manifestFile,
                              ObjectMapper objectMapper,
                              boolean loadOnAccess) {
        this.manifestFile = manifestFile;
        this.objectMapper = objectMapper;
        this.loadOnAccess = loadOnAccess;
        load();
    }

    public Manifest getManifest() {
        if(loadOnAccess) {
            load();
        }

        return manifest;
    }

    public void load() {
        try {
            logger.info("Reading manifest.json.");
            this.manifest = new Manifest(this.objectMapper.readValue(manifestFile.getInputStream(), new TypeReference<HashMap<String, String>>() { }));
        } catch (IOException e) {
            logger.warn("Unable to load manifest.json file from disk, using an empty map.");
            this.manifest = new Manifest(new HashMap<>());
        }
    }

}
