package com.ln.presentation.domain;

public class OfflineCreditApplicationHandler {

    public void handle(CreditApplication application) {
        System.out.println("Handle and process offline credit application: " + application.getUid());
    }

}
