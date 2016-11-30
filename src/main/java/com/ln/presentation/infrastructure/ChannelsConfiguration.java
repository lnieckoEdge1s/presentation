package com.ln.presentation.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

import static com.ln.presentation.infrastructure.Channels.CHANNEL_APPLICATION_INPUT;

@Configuration
public class ChannelsConfiguration {

    @Bean(name = CHANNEL_APPLICATION_INPUT)
    public MessageChannel channelApplicationInput() {
        return new DirectChannel();
    }
}
