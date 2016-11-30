package com.ln.presentation.infrastructure;


import com.ln.presentation.domain.CreditApplicationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    CreditApplicationHandler applicationHandler() {
        return new CreditApplicationHandler();
    }
}
