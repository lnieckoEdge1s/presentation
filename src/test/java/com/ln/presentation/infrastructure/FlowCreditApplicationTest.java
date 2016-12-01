package com.ln.presentation.infrastructure;


import com.ln.presentation.domain.CreditApplication;
import com.ln.presentation.domain.OfflineCreditApplicationHandler;
import com.ln.presentation.domain.OnlineCreditApplicationHandler;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {IntegrationConfiguration.class, FlowTestConfigurationMock.class})
public class FlowCreditApplicationTest {
    private static final String ID = "546346757";
    private static final String NAME = "Lukasz";
    private static final String AMOUNT = "1000";

    @Autowired
    private InboundCustomSourceGateway gateway;

    @Autowired
    private OfflineCreditApplicationHandler offlineCreditApplicationHandler;

    @Autowired
    private OnlineCreditApplicationHandler onlineCreditApplicationHandler;

    private ArgumentCaptor<CreditApplication> argumentCaptor = ArgumentCaptor.forClass(CreditApplication.class);

    @Test
    public void shouldProcessOfflineApplication() {
        //given
        CreditApplicationRepresentation creditApplicationRepresentation = prepareApplication();

        //when
        gateway.send(creditApplicationRepresentation);

        //then
        Mockito.verify(offlineCreditApplicationHandler).handle(argumentCaptor.capture());
        verifyZeroInteractions(onlineCreditApplicationHandler);

        CreditApplication value = argumentCaptor.getValue();
        assertThat(value.getAmount(), equalTo(new BigDecimal(AMOUNT)));
        assertThat(value.getUid(), equalTo(ID));
        assertThat(value.getName(), equalTo(NAME));

    }

    private CreditApplicationRepresentation prepareApplication() {
        CreditApplicationRepresentation creditApplicationRepresentation = new CreditApplicationRepresentation();
        creditApplicationRepresentation.setAmount(AMOUNT);
        creditApplicationRepresentation.setSurname("Niecko");
        creditApplicationRepresentation.setName(NAME);
        creditApplicationRepresentation.setId(ID);
        return creditApplicationRepresentation;
    }

}