package com.globant.trainingnewgen;


import com.globant.trainingnewgen.model.User;
import com.globant.trainingnewgen.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class TrainingNewGenApplication {

    private final UserService userService;

    public TrainingNewGenApplication(UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TrainingNewGenApplication.class, args);
    }





}
