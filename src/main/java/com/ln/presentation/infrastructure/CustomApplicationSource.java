package com.ln.presentation.infrastructure;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CustomApplicationSource implements InitializingBean {

    private static final int AMOUNT = 3;
    private List<CreditApplicationRepresentation> applications = new ArrayList<>(AMOUNT);

    private InboundCustomSourceGateway gateway;
    private ThreadPoolTaskScheduler taskScheduler;

    public CustomApplicationSource(InboundCustomSourceGateway gateway) {
        this.gateway = gateway;
    }

    @Override
    public void afterPropertiesSet() {
        taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("CreditSourceJob");
        taskScheduler.initialize();
        applications.add(sourceCreditApp("Lukasz", "Niecko", 10000.50));
        applications.add(sourceCreditApp("Pawel", "Kowalski", 50000));
        applications.add(sourceCreditApp("Michal", "Janowski", 50000));
        taskScheduler.scheduleAtFixedRate(() -> {
            int index = new Random().nextInt(applications.size());
            gateway.send(applications.get(index));
        }, Date.from(LocalDateTime.now().plusSeconds(5)
                .atZone(ZoneId.systemDefault())
                .toInstant()), 2000);
    }

    private CreditApplicationRepresentation sourceCreditApp(String name, String surname, double amount) {
        CreditApplicationRepresentation applicationRepresentation = new CreditApplicationRepresentation();
        applicationRepresentation.setId(String.format("UID/3210/%s", new Random().nextInt()));
        applicationRepresentation.setName(name);
        applicationRepresentation.setSurname(surname);
        applicationRepresentation.setAmount(String.valueOf(amount));
        return applicationRepresentation;
    }

}