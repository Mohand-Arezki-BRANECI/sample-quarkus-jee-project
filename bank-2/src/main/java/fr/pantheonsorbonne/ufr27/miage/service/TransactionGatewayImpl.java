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
    public String makeTransaction(TransactionDTO transaction){
        String transferPurpose = transaction.getTransactionPurpose();

        try (ProducerTemplate producer = context.createProducerTemplate()) {
            producer.sendBodyAndHeader("direct:sendTransactionToAllBanks", transaction, "transferPurpose", transferPurpose);
        } catch (IOException e) {
            return "There was a problem with the generation of the transaction request. Please try later again.";
        }

        String responseMessage = switch (transferPurpose) {
            case "clientPayment" -> "Your payment request for Booking has been registered. Please stand by...";
            case "bookingPayment" -> "The payment request for the Hotel has been registered. Please stand by...";
            case "cancellation" -> "Your refund request has been registered. Please stand by...";
            default ->  "Your Transaction request is of unknown nature.";
        };

        return responseMessage;
    }

    @Override
    public boolean isSendingBank(TransactionDTO transaction) {
        long fromBank = transaction.getFromBankId();

        Bank thisBank = bankService.getBankObject();

        return fromBank == thisBank.getBankId();
    }

    @Override
    public boolean isReceivingBank(TransactionDTO transaction) {
        long toBankId = transaction.getToBankId();

        Bank thisBank = bankService.getBankObject();

        return toBankId == thisBank.getBankId();
    }

    @Override
    public boolean isSendingAndReceiving(TransactionDTO transaction){
        long toBankId = transaction.getToBankId();
        long fromBank = transaction.getFromBankId();

        Bank thisBank = bankService.getBankObject();

        return toBankId == thisBank.getBankId() && fromBank == thisBank.getBankId();
    }
}
