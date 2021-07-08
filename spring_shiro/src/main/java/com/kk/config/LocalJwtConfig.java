package com.kk.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;

/**
 * LocalJwtConfig
 *
 * @author kkmystery
 * @version 1.0 2021/7/8
 * @since 1.0.0
 */
@Component
@ConfigurationProperties(prefix = "local.jwt")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocalJwtConfig {
    private Duration expired;
    private String secret;
}
