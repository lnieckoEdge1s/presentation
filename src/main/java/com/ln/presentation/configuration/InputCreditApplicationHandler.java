package com.ln.presentation.configuration;

import com.ln.presentation.CreditApplication;
import org.springframework.integration.dsl.support.GenericHandler;

import java.util.Map;

public class InputCreditApplicationHandler implements GenericHandler<CreditApplication> {
    @Override
    public Object handle(CreditApplication application, Map<String, Object> headers) {
        System.out.println(application);
        return "<Result>" + application.getUid() + "</Result>";
    }
}
