package com.dmoffat.view.manifest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;

/**
 * Manifest interceptor integration tests.
 *
 * Ensures the ManifestFileLoaderInterceptor is autowired correctly and that the contents of the manifest file are added
 * to the request.
 *
 * todo: mock the database
 * todo: use mocked beans
 *
 * @author danielmoffat
 */
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(value = "test")
public class ManifestInterceptorTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManifestFileLoaderInterceptor manifestFileLoaderInterceptor;

    @Test
    public void contextLoads() throws Exception {
        assertNotNull(manifestFileLoaderInterceptor);
    }

    @Test
    public void staticPathsAreAddedToTheRequest() throws Exception {
        this.mockMvc
                .perform(get("/"))
                .andExpect(request().attribute("static.css_path", "/styles/app.a0758bb9efdea1dbe42dcb66b9359b7c.css"))
                .andExpect(request().attribute("static.js_path", "/scripts/app.a0758bb9efdea1dbe42dcb66b9359b7c.js"));

    }
}
