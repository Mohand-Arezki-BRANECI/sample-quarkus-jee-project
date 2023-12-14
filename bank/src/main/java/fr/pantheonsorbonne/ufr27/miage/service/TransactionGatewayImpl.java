package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.Booking;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.control.ActivateRequestContext;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.IOException;

@ApplicationScoped
public class TransactionGatewayImpl implements TransactionGateway {

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;
    @Inject
    CamelContext context;

    @Inject
    protected BankService bankService;

    @Override
    public void sendTransaction(TransactionDTO transaction){
        try (ProducerTemplate producer = context.createProducerTemplate()) {
            producer.sendBody("direct:sendTransactionToAllBanks", transaction);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean shouldProcess(TransactionDTO transaction) {
        long toBankId = transaction.getToBankId();

        Bank thisBank = bankService.getBankObject();
        System.out.println(thisBank.getBankname());
        // TODO: Add bankID

        return toBankId == thisBank.getBankId();
    }
}
