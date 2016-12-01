package com.ln.presentation.domain;

public class OnlineCreditApplicationHandler {
    public String handle(CreditApplication application) {
        System.out.println("Online credit application handler");
        return "<Result>" + application.getUid() + "</Result>";
    }
}
