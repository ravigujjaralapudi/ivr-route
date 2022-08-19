package com.ivr.router.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Data
@Configuration
public class AppConfig {

    @Value("${config.app.scopeDescription}")
    private String scopeDescription;

    @Value("${config.app.scope}")
    private String scope;

    @Value("${config.oauth2.accessTokenUri}")
    private URI accessTokenUri;

}
