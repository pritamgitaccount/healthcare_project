package com.doctorbookingapp;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;


@SpringBootApplication
public class HospitalManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(HospitalManagementApplication.class, args);
      }
    @Bean   // Use When third party library added @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


}
