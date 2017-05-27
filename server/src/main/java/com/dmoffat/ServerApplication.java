package com.dmoffat;

import com.dmoffat.view.manifest.ManifestFileLoader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;

@SpringBootApplication
public class ServerApplication {

	@Value(value = "classpath:manifest.json")
	private Resource manifestFile;

	@Bean
	public ManifestFileLoader manifestFileLoader() {
		return new ManifestFileLoader(manifestFile, new ObjectMapper(), true);
	}

	public static void main(String[] args) {
		SpringApplication.run(ServerApplication.class, args);
	}
}
