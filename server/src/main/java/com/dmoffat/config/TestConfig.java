package com.dmoffat.config;

import com.dmoffat.view.manifest.Manifest;
import com.dmoffat.view.manifest.ManifestFileLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test configuration used solely for tests.
 *
 * @author danielmoffat
 */
@Configuration
@Profile(value = "test")
public class TestConfig {

    @Bean
    public ManifestFileLoader manifestFileLoader() throws IOException {
        ManifestFileLoader manifestFileLoader = mock(ManifestFileLoader.class);

        Map<String, String> map = new HashMap<>();
        map.put("/app.js", "/scripts/app.a0758bb9efdea1dbe42dcb66b9359b7c.js");
        map.put("/app.css", "/styles/app.a0758bb9efdea1dbe42dcb66b9359b7c.css");

        when(manifestFileLoader.getManifest()).thenReturn(new Manifest(map));

        return manifestFileLoader;
    }
}
