package com.ln.presentation.domain;

import java.util.Map;

public class CreditApplicationHandler {
    public String handle(CreditApplication application, Map<String, Object> headers) {
        System.out.println(application);
        return "<Result>" + application.getUid() + "</Result>";
    }
}
