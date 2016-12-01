package com.ln.presentation.infrastructure;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface InboundCustomSourceGateway {

    @Gateway(requestChannel = Channels.CHANNEL_CUSTOM_SOURCE_INPUT)
    void send(CreditApplicationRepresentation creditApplicationRepresentation);

}
