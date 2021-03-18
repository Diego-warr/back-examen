package com.proyect.configuration;

import com.proyect.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserDataConfig {

    @Bean
    public User userData()
    {
        return new User();
    }
}
