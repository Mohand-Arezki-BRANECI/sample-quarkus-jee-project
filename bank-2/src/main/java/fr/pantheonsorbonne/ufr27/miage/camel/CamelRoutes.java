package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dto.Booking;
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
        camelContext.setTracing(true);

        from("direct:sendTransactionToAllBanks")//
                .marshal().json()
                .to("sjms2:topic:transactionTopic")
                .log("Transaction successfully sent to all available banks.");

        from("sjms2:topic:transactionTopic")
                .unmarshal().json(TransactionDTO.class)
                .filter()
                .method(TransactionGateway.class,"shouldProcess")
                .to("direct:processMessage")
                .end();

        from("direct:processMessage")
                .log("Transaction received")
                .bean(BankService.class, "handleReceivedTransaction")
                .marshal().json()
                .log("Transaction added and balance updated");
    }
}
