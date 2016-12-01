package com.ln.presentation.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageChannel;

import static com.ln.presentation.infrastructure.Channels.CHANNEL_APPLICATION_INPUT;
import static com.ln.presentation.infrastructure.Channels.CHANNEL_CUSTOM_SOURCE_INPUT;

@Configuration
public class ChannelsConfiguration {

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller() {
        return Pollers.fixedRate(500).get();
    }

    @Bean(name = CHANNEL_APPLICATION_INPUT)
    public MessageChannel channelApplicationInput() {
        return MessageChannels.queue(100).get();
    }

    @Bean(name = CHANNEL_CUSTOM_SOURCE_INPUT)
    public MessageChannel channelCustomSourceInput() {
        return new DirectChannel();
    }

}
