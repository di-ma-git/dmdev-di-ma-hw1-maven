package com.dima.config;

import com.dima.PharmacyApp;
import java.util.Optional;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
@EnableEnversRepositories(basePackageClasses = PharmacyApp.class)
public class AuditConfiguration {

    @Bean
    public AuditorAware<String> auditorAware() {
        // TODO SecurityContext.getCurrentUser().getUsername();
        return () -> {
            return Optional.of("someuser");
        };
    }
}
