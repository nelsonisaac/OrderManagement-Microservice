package com.Nelson.product_service.variables;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvEntry;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
//public class DotenvConfig {

//    @PostConstruct
//    @Bean
//    public CommandLineRunner commandLineRunner(){
//        return args -> {
//            Dotenv dotenv = Dotenv.load();
//
//            for (DotenvEntry entry: dotenv.entries()){
//                String key = entry.getKey();
//                String value = entry.getValue();
//
//                if(System.getProperty(key) == null){
//                    System.setProperty(key, value);
//                }
//            }
//        };

//    }
//}
