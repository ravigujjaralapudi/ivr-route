package com.ivr.router.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.paths.RelativePathProvider;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private final ServletContext servletContext;

    @Autowired
    private AppConfig appConfig;

    @Value("${config.swagger.base.path}")
    private String basePath;

    @Autowired
    public SwaggerConfig(ServletContext servletContext) {
        this.servletContext = servletContext;
    }

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("IVR-ROUTER-APIs")
                .pathProvider(new RelativePathProvider(servletContext) {
                    @Override
                    public String getApplicationBasePath() {
                        return basePath;
                    }
                })
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(Arrays.asList(securitySchema()));
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        final AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = new AuthorizationScope(appConfig.getScope(), appConfig.getScopeDescription());
        return Collections.singletonList(new SecurityReference("oauth2", authorizationScopes));
    }

    private OAuth securitySchema() {
        List<AuthorizationScope> authorizationScopeList = new ArrayList<>();
        authorizationScopeList.add(new AuthorizationScope(appConfig.getScope(), appConfig.getScopeDescription()));
        List<GrantType> grantTypes = new ArrayList();
        GrantType clientCredentialsGrant = new ClientCredentialsGrant(appConfig.getAccessTokenUri().toString());
        grantTypes.add(clientCredentialsGrant);
        return new OAuth("oauth2", authorizationScopeList, grantTypes);
    }
}
