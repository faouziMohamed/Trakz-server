package com.trakz.server;

import com.trakz.server.entities.Folder;
import com.trakz.server.repositories.FolderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class TrakzServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(TrakzServerApplication.class, args);
  }

  @Bean
  CommandLineRunner run(FolderRepository folderRepository) {
    System.out.println("Trakz Server is running...");
    return args -> {
      // Add default folders if none exist
      // check if tasks folder exists in database, if not create it
      var isTasksFolderPresent = folderRepository.findByNameIgnoreCase("Projects");
      if (isTasksFolderPresent == null) {
        // create tasks folder
        folderRepository.save(Folder.builder()
          .name("Projects")
          .description("An example of a folder that contains for task outside the main folder (Tasks)")
          .build());
      }

      // check if tasks folder exists in database, if not create it
      isTasksFolderPresent = folderRepository.findByNameIgnoreCase("Tasks");
      if (isTasksFolderPresent == null) {
        // create tasks folder
        folderRepository.save(Folder.builder()
          .name("Tasks")
          .description("Default folder that contains all tasks that are not in a folder")
          .build());
      }

      // check if projects folder exists in database, if not create it
      var folders = folderRepository.findAll();
      folders.forEach(System.out::println);
    };
  }

  @Bean
  public CorsFilter corsFilter() {
    UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.setAllowCredentials(true);
    corsConfiguration.setAllowedOrigins(Arrays.asList(
      "http://localhost:4200", "https://trakz.vercel.app",
      "https://trakz.mfaouzi.com", "https://trakz-git-main-faouzimohamed.vercel.app")
    );

    corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
      "Accept", "Jwt-Token", "Authorization", "Origin, Accept", "X-Requested-With",
      "Access-Control-Request-Method", "Access-Control-Request-Headers"));

    corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type",
      "Accept", "Jwt-Token", "Authorization", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials",
      "Filename"));
    corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    return new CorsFilter(urlBasedCorsConfigurationSource);
  }
}