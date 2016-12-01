package com.ln.presentation.infrastructure.transformer;

import com.ln.presentation.domain.CreditApplication;
import com.ln.presentation.infrastructure.CreditApplicationRepresentation;

import java.math.BigDecimal;

public class CustomApplicationTransformer implements org.springframework.integration.transformer.GenericTransformer<CreditApplicationRepresentation, CreditApplication> {

    public CreditApplication transform(CreditApplicationRepresentation p) {
        CreditApplication application = new CreditApplication();
        application.setName(p.getName());
        application.setAmount(new BigDecimal(p.getAmount()));
        application.setSurnname(p.getSurname());
        application.setUid(p.getId());
        return application;
    }

}