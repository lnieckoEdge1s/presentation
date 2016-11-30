package com.ln.presentation.infrastructure.transformer;

import com.ln.presentation.domain.CreditApplication;
import org.springframework.integration.transformer.GenericTransformer;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.transform.dom.DOMSource;
import java.math.BigDecimal;

public class CreditApplicationWSInputTransformer implements GenericTransformer<DOMSource, CreditApplication> {

    public CreditApplication transform(DOMSource s) {
        Node node = s.getNode();
        String prefix = node.getPrefix();
        Document document = node.getOwnerDocument();
        String name = document.getElementsByTagName(prefix + ":name").item(0).getTextContent();
        String surname = document.getElementsByTagName(prefix + ":surname").item(0).getTextContent();
        String uid = document.getElementsByTagName(prefix + ":uuid").item(0).getTextContent();
        String amount = document.getElementsByTagName(prefix + ":amount").item(0).getTextContent();
        CreditApplication creditApplication = new CreditApplication();
        creditApplication.setUid(uid);
        creditApplication.setName(name);
        creditApplication.setAmount(new BigDecimal(amount));
        creditApplication.setSurnname(surname);
        return creditApplication;
    }
}
