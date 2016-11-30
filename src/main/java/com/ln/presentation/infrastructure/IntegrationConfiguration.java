package com.ln.presentation.infrastructure;

import com.ln.presentation.domain.CreditApplication;
import com.ln.presentation.domain.CreditApplicationHandler;
import com.ln.presentation.infrastructure.transformer.CreditApplicationWSInputTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ws.SimpleWebServiceInboundGateway;
import org.springframework.messaging.MessageChannel;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.endpoint.MessageEndpoint;

@IntegrationComponentScan
@Configuration
public class IntegrationConfiguration extends WsConfigurerAdapter {

    @Bean
    public MessageEndpoint webServiceinboundGateway(MessageChannel input) {
        SimpleWebServiceInboundGateway simpleWebServiceInboundGateway = new SimpleWebServiceInboundGateway();
        simpleWebServiceInboundGateway.setRequestChannel(input);
        return simpleWebServiceInboundGateway;
    }

    @Bean
    public IntegrationFlow applicationFlow(CreditApplicationHandler applicationHandler) {
        return IntegrationFlows.from(Channels.CHANNEL_APPLICATION_INPUT)
                .transform(p -> new CreditApplicationWSInputTransformer())
                .handle(CreditApplication.class, applicationHandler::handle).get();
    }

//    @Bean
//    public IntegrationFlow applicationFlow(CreditApplicationHandler applicationHandler) {
//        return f -> f.channel(Channels.CHANNEL_APPLICATION_INPUT)
//                .handle(CreditApplication.class, applicationHandler::handle);
//    }

}
