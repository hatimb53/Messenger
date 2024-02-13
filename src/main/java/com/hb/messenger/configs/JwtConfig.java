package com.hb.messenger.configs;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "jwt.token")
@Configuration("jwtProperties")
@Data
public class JwtConfig {
  private String secret;
  private Long expiry;

}
