package com.ln.presentation.infrastructure;


import com.ln.presentation.domain.OfflineCreditApplicationHandler;
import com.ln.presentation.domain.OnlineCreditApplicationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

    @Bean
    OfflineCreditApplicationHandler offlineApplicationHandler() {
        return new OfflineCreditApplicationHandler();
    }

    @Bean
    OnlineCreditApplicationHandler onlineApplicationHandler() {
        return new OnlineCreditApplicationHandler();
    }
}
