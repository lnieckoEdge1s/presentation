package com.ln.presentation.infrastructure;

import org.springframework.messaging.MessageHeaders;

public abstract class RoutingCreditApplicationChooser {
    public static String ONLINE_MODE = "ONLINE";
    public static final String OFFLINE_MODE = "OFFLINE";
    public static final String MODE_HEADER = "type";

    public static boolean isOnline(MessageHeaders messageHeaders) {
        String mode = (String) messageHeaders.get(MODE_HEADER);
        return ONLINE_MODE.equals(mode);
    }

    public static boolean isOffline(MessageHeaders messageHeaders) {
        String mode = (String) messageHeaders.get(MODE_HEADER);
        return OFFLINE_MODE.equals(mode);
    }
}
