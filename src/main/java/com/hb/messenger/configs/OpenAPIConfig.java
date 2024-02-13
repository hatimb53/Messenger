package com.hb.messenger.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Simple Messenger System", description = " Provides both one-on-one  and group chat functionality "))
public class OpenAPIConfig {
}