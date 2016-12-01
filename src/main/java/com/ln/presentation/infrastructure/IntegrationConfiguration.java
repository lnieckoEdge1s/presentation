package com.ln.presentation.infrastructure;

import com.ln.presentation.domain.CreditApplication;
import com.ln.presentation.domain.CreditApplicationHandler;
import com.ln.presentation.infrastructure.transformer.CreditApplicationWSInputTransformer;
import com.ln.presentation.infrastructure.transformer.CustomApplicationTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.ws.SimpleWebServiceInboundGateway;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.endpoint.MessageEndpoint;

@IntegrationComponentScan
@Configuration
public class IntegrationConfiguration extends WsConfigurerAdapter {

    @Bean
    public MessageEndpoint webServiceInboundGateway() {
        SimpleWebServiceInboundGateway simpleWebServiceInboundGateway = new SimpleWebServiceInboundGateway();
        simpleWebServiceInboundGateway.setRequestChannelName(Channels.CHANNEL_WEB_SERVICE_INPUT);
        return simpleWebServiceInboundGateway;
    }

    @Bean
    public CustomApplicationSource customApplicationSource(InboundCustomSourceGateway gateway) {
        return new CustomApplicationSource(gateway);
    }

    @Bean
    public IntegrationFlow webServiceInputFlow() {
        return IntegrationFlows.from(Channels.CHANNEL_WEB_SERVICE_INPUT)
                .transform(new CreditApplicationWSInputTransformer())
                .channel(Channels.CHANNEL_APPLICATION_INPUT).get();
    }

    @Bean
    public IntegrationFlow customSourceApplicationFlow() {
        return IntegrationFlows.from(Channels.CHANNEL_CUSTOM_SOURCE_INPUT)
                .transform(new CustomApplicationTransformer())
                .channel(Channels.CHANNEL_APPLICATION_INPUT).get();
    }

    @Bean
    public IntegrationFlow applicationFlow(CreditApplicationHandler applicationHandler) {
        return IntegrationFlows.from(Channels.CHANNEL_APPLICATION_INPUT)
                .handle(CreditApplication.class, applicationHandler::handle).get();
    }

//    @Bean
//    public IntegrationFlow applicationFlow(CreditApplicationHandler applicationHandler) {
//        return f -> f.channel(Channels.CHANNEL_APPLICATION_INPUT)
//                .transform(p -> new CreditApplicationWSInputTransformer())
//                .handle(CreditApplication.class, applicationHandler::handle);
//    }

}
