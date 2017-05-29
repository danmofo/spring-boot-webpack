package com.dmoffat.config;

import com.dmoffat.view.manifest.ManifestFileLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;

/**
 * Manifest configuration for production and dev profiles.
 *
 * @author danielmoffat
 */
@Configuration
public class ManifestConfig {

    @Bean
    @Profile("production")
    public ManifestFileLoader productionManifestFileLoader(@Value(value = "classpath:manifest.json") Resource manifestFile,
                                                 ObjectMapper objectMapper) {

        return new ManifestFileLoader(manifestFile, objectMapper, false);
    }

    @Bean
    @Profile("dev")
    public ManifestFileLoader devManifestFileLoader(@Value(value = "classpath:manifest.json") Resource manifestFile,
                                                 ObjectMapper objectMapper) {

        return new ManifestFileLoader(manifestFile, objectMapper, true);
    }



}
