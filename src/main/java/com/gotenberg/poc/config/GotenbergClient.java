package com.gotenberg.poc.config;

import dev.inaka.Jotenberg;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.MalformedURLException;


@Configuration
public class GotenbergClient {

    @Bean
    public Jotenberg jotenbergClient() throws MalformedURLException {
        return new Jotenberg("http://localhost:3000/");
    }
}
