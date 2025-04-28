package com.felixramos.album_management_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "com.felixramos.album_management_api.entity")
public class AlbumManagementApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(AlbumManagementApiApplication.class, args);
	}
}