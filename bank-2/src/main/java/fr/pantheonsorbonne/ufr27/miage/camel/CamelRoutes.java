package fr.pantheonsorbonne.ufr27.miage.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.service.BankService;
import fr.pantheonsorbonne.ufr27.miage.service.TransactionGateway;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    CamelContext camelContext;

    @Override
    public void configure() throws Exception {

        from("direct:sendTransactionToAllBanks")//
                .marshal().json()
                .to("sjms2:topic:transactionTopic")
                .log("Transaction successfully sent to all available banks.");


        from("sjms2:topic:transactionTopic")
                .unmarshal().json(TransactionDTO.class)
                .choice()
                    .when().method(TransactionGateway.class, "isSendingAndReceiving")
                        .to("direct:processSendingAndReceiving")
                    .when().method(TransactionGateway.class, "isSendingBank")
                        .to("direct:processSending")
                    .when().method(TransactionGateway.class, "isReceivingBank")
                        .to("direct:processReceiving")
                    .otherwise()
                        .log("No valid condition matched. Dropping the message.")
                .endChoice();

        from("direct:processSendingAndReceiving")
                .bean(BankService.class, "handleSendAndReceiveTransaction")
                .marshal().json()
                .log("Transaction has been successfully processed")
                .to("direct:responseEndpoint");


        from("direct:processSending")
                .bean(BankService.class, "handleSendTransaction")
                .marshal().json()
                .log("Transaction has been sent");

        from("direct:processReceiving")
                .bean(BankService.class, "handleReceivedTransaction")
                .marshal().json()
                .log("Transaction has been received")
                .to("direct:responseEndpoint");

        from("direct:responseEndpoint")
                .choice()
                    .when(header("transferPurpose").isEqualTo("clientPayment"))
                        .to("sjms2:topic:clientPaymentResponse")
                    .when(header("transferPurpose").isEqualTo("bookingPayment"))
                        .to("sjms2:topic:bookingPaymentResponse")
                    .when(header("transferPurpose").isEqualTo("cancellation"))
                        .to("sjms2:topic:cancellationPaymentResponse")
                .end();

    }


}
