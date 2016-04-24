package ru.niyaz.websocket.config;

/**
 * Created by user on 20.04.2016.
 */

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.social.security.SpringSocialConfigurer;


/**
 * Created by User on 15.01.2016.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .authorizeRequests()
                .antMatchers("/*").permitAll()
                .and()
                .csrf().disable()

        //.rememberMe().tokenRepository(persistentTokenRepository())
        ;
    }

}
