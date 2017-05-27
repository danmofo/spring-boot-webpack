package com.dmoffat.view.manifest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Adds request attributes containing the paths to our static assets, by making use of a ManifestFileLoader.
 *
 * @author danielmoffat
 */
@Component
public class ManifestFileLoaderInterceptor extends HandlerInterceptorAdapter {

    private ManifestFileLoader manifestFileLoader;

    @Autowired
    public ManifestFileLoaderInterceptor(ManifestFileLoader manifestFileLoader) {
        this.manifestFileLoader = manifestFileLoader;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Manifest manifest = manifestFileLoader.getManifest();

        request.setAttribute("static.css_path", manifest.getForPath("app.css"));
        request.setAttribute("static.js_path", manifest.getForPath("app.js"));
        return true;
    }
}
