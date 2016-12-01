package com.ln.presentation.domain;

import java.util.Map;

public class CreditApplicationHandler {
    public String handle(CreditApplication application, Map<String, Object> headers) {
        System.out.println(application);
        if (headers.containsKey("replyChannel")) {
            return "<Result>" + application.getUid() + "</Result>";
        } else {
            return null;
        }
    }
}
