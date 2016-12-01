package com.ln.presentation.infrastructure;

import com.ln.presentation.domain.CreditApplication;
import com.ln.presentation.domain.OfflineCreditApplicationHandler;
import com.ln.presentation.domain.OnlineCreditApplicationHandler;
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
                .enrichHeaders(enricher -> enricher.header(RoutingCreditApplicationChooser.MODE_HEADER, RoutingCreditApplicationChooser.ONLINE_MODE))
                .channel(Channels.CHANNEL_APPLICATION_INPUT).get();
    }

    @Bean
    public IntegrationFlow customSourceApplicationFlow() {
        return IntegrationFlows.from(Channels.CHANNEL_CUSTOM_SOURCE_INPUT)
                .transform(new CustomApplicationTransformer())
                .enrichHeaders(enricher -> enricher.header(RoutingCreditApplicationChooser.MODE_HEADER, RoutingCreditApplicationChooser.OFFLINE_MODE))
                .channel(Channels.CHANNEL_APPLICATION_INPUT).get();
    }

    @Bean
    public IntegrationFlow applicationFlow(OnlineCreditApplicationHandler onlineCreditApplicationHandler,
                                           OfflineCreditApplicationHandler offlineCreditApplicationHandler
                                           ) {
        return IntegrationFlows.from(Channels.CHANNEL_APPLICATION_INPUT)
                .routeToRecipients(r -> r
                        .recipientFlow(message -> RoutingCreditApplicationChooser.isOnline(message.getHeaders()),
                                sf -> sf.channel(Channels.CHANNEL_ONLINE_APP_INPUT)
                                                .handle(CreditApplication.class, (p, h) -> onlineCreditApplicationHandler.handle(p)))
                        .recipientFlow(message -> RoutingCreditApplicationChooser.isOffline(message.getHeaders()),
                                sf -> sf.channel(Channels.CHANNEL_OFFLINE_APP_INPUT)
                                                .handle(CreditApplication.class, (p, h) -> {offlineCreditApplicationHandler.handle(p); return null;})))
                .get();
    }
}
