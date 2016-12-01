package com.ln.presentation.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import static com.ln.presentation.infrastructure.Channels.CHANNEL_APPLICATION_INPUT;
import static com.ln.presentation.infrastructure.Channels.CHANNEL_CUSTOM_SOURCE_INPUT;

@Configuration
public class ChannelsConfiguration {

    @Bean(name = CHANNEL_APPLICATION_INPUT)
    public MessageChannel channelApplicationInput() {
        return new DirectChannel();
    }

    @Bean(name = CHANNEL_CUSTOM_SOURCE_INPUT)
    public MessageChannel channelCustomSourceInput() {
        return new DirectChannel();
    }

}
