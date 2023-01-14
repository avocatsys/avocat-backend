package com.avocat.security.config;

import com.avocat.security.custom.CustomAuditAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing(auditorAwareRef="auditorAware")
public class PersistenceAuditConfig {

    @Bean
    public AuditorAware<String> auditorAware(){
        return new CustomAuditAwareImpl();
    }
}
