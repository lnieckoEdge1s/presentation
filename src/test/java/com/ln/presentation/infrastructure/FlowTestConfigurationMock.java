package com.ln.presentation.infrastructure;

import com.ln.presentation.domain.OfflineCreditApplicationHandler;
import com.ln.presentation.domain.OnlineCreditApplicationHandler;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
public class FlowTestConfigurationMock {

    @Bean
    OnlineCreditApplicationHandler onlineCreditApplicationHandler() {
        return Mockito.mock(OnlineCreditApplicationHandler.class);
    }

    @Bean
    OfflineCreditApplicationHandler offlineCreditApplicationHandler() {
        return Mockito.mock(OfflineCreditApplicationHandler.class);
    }
}