package com.ln.presentation.configuration;

import com.ln.presentation.CreditApplicationWSInputTransformer;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.interceptor.WireTap;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.support.Transformers;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.integration.ws.SimpleWebServiceInboundGateway;
import org.springframework.messaging.MessageChannel;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.endpoint.MessageEndpoint;
import org.springframework.ws.server.endpoint.mapping.UriEndpointMapping;
import org.springframework.ws.transport.http.MessageDispatcherServlet;

@EnableWs
@IntegrationComponentScan
@Configuration
public class IntegrationConfiguration extends WsConfigurerAdapter {

    @Bean
    public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean(servlet, "/ws/*");
    }

    @Bean
    public UriEndpointMapping uriEndpointMapping(MessageEndpoint inboundGateway) {
        UriEndpointMapping mapping = new UriEndpointMapping();
        mapping.setDefaultEndpoint(inboundGateway);
        return mapping;
    }

    @Bean
    public MessageEndpoint inboundGateway(MessageChannel input) {
        SimpleWebServiceInboundGateway simpleWebServiceInboundGateway = new SimpleWebServiceInboundGateway();
        simpleWebServiceInboundGateway.setRequestChannel(input);
        return simpleWebServiceInboundGateway;
    }

    @Bean
    public MessageChannel input() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow applicationFlow() {
        return f -> f.channel("input")
                .transform(new CreditApplicationWSInputTransformer())
                .handle(new InputCreditApplicationHandler());
    }

}
