package com.dmoffat.config;

import com.dmoffat.view.manifest.ManifestFileLoaderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author danielmoffat
 */

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    private ManifestFileLoaderInterceptor manifestFileLoaderInterceptor;

    public WebMvcConfig(ManifestFileLoaderInterceptor manifestFileLoaderInterceptor) {
        this.manifestFileLoaderInterceptor = manifestFileLoaderInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(manifestFileLoaderInterceptor);
    }
}
