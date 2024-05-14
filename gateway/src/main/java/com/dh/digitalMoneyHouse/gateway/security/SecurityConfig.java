package com.dh.digitalMoneyHouse.gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {

        http.authorizeExchange(
             authorizeExchangeSpec -> authorizeExchangeSpec
                .pathMatchers("/user/register").permitAll()
                .pathMatchers("/user/login").permitAll()
                .anyExchange().authenticated()
                )
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(ServerHttpSecurity.CorsSpec::disable)
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .jwkSetUri("http://localhost:8080/realms/dh-money-users/protocol/openid-connect/certs")
                        )
                );
        return http.build();
    }

}
