package com.dmoffat.view.manifest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Manifest file unit tests.
 *
 * Essentially checks the manifest.json file can be read, and paths retrieved.
 *
 * @author danielmoffat
 */
@RunWith(JUnit4.class)
public class ManifestTests {
    public static final String MANIFEST_CONTENTS = "{\"/app.css\": \"/styles/app.9864c5e5dcf0f2c05321.css\",\"/app.js\": \"/scripts/app.a8d89e2bdda7ffe32062.js\",\"/vendor.js\": \"/scripts/vendor.dc356b8b7cd146335928.js\"}\n";

    private Manifest manifest;

    @Before
    public void setUp() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("/app.js", "/scripts/app.a0758bb9efdea1dbe42dcb66b9359b7c.js");
        map.put("/app.css", "/styles/app.a0758bb9efdea1dbe42dcb66b9359b7c.css");

        manifest = new Manifest(map);
    }

    @Test
    public void getForPath() throws Exception {
        assertNotNull(manifest.getForPath("app.js"));
        assertNotNull(manifest.getForPath("app.css"));
        assertNull(manifest.getForPath("non-existent-path.foo"));
    }

    @Test
    public void manifestFileLoader() throws Exception {

        // Mock objects
        Resource mockResource = Mockito.mock(Resource.class);

        Mockito.when(mockResource.getInputStream())
                .thenReturn(new ByteArrayInputStream(MANIFEST_CONTENTS.getBytes(Charset.forName("utf-8"))));

        ObjectMapper objectMapper = new ObjectMapper();

        // Test that the file is loaded only once by changing the underlying file
        ManifestFileLoader cachedLoader = new ManifestFileLoader(mockResource, objectMapper, false);
        Manifest manifest = cachedLoader.getManifest();

        Mockito.when(mockResource.getInputStream())
                .thenReturn(new ByteArrayInputStream("{}".getBytes(Charset.forName("utf-8"))));

        Manifest manifest1 = cachedLoader.getManifest();

        assertEquals(manifest, manifest1);
    }
}
