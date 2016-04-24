package ru.niyaz.websocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.naming.InitialContext;

/**
 * Created by user on 16.04.2016.
 */

@Configuration
@Import({SecurityConfig.class, RepositoryConfig.class})
@ComponentScan({"ru.niyaz.websocket.service"})
public class AppConfig {

    @Bean
    public InitialContext initialContext() {
        try {
            return new InitialContext();
        } catch (Exception ex) {
            return null;
        }
    }
}
