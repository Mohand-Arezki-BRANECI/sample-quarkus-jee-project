package top.net.camel;

import fr.pantheonsorbonne.ufr27.miage.dto.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;


import top.net.cli.UserInterface;
import top.net.service.TicketingService;


import java.util.HashMap;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.vendorId")
    Integer vendorId;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.user")
    String smtpUser;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.password")
    String smtpPassword;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.host")
    String smtpHost;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.port")
    String smtpPort;
    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.smtp.from")
    String smtpFrom;

    @Inject
    top.net.camel.handler.BookingResponseHandler BookingResponseHandler;

    @Inject
    TicketingService ticketingService;

    @Inject
    UserInterface eCommerce;

    @Inject
    CamelContext camelContext;


    @Override
    public void configure() throws Exception {
        camelContext.setTracing(false);

        from("sjms2:topic:clientPaymentResponse")
                .unmarshal().json(TransactionDTO.class)
                .log(LoggingLevel.INFO,"${body}");

        from("sjms2:topic:bookingPaymentResponse")
                .unmarshal().json(TransactionDTO.class)
                .log(LoggingLevel.INFO,"${body}");

        from("sjms2:topic:cancellationPaymentResponse")
                .unmarshal().json(TransactionDTO.class)
                .log(LoggingLevel.INFO,"${body}");

        from("direct:bookingFront")
                .choice()
                .when(header("loginStatus").isEqualTo("bookingLoginError"))
                .bean(eCommerce, "showErrorMessage").stop()
                .when(header("loginStatus").isEqualTo("bookingLoginSuccess"))
                .bean(eCommerce, "showSuccessMessage('Welcome to Booking ${body.getFirstName()} ${body.getLastName()}')")
                .bean(eCommerce, "askForHotelLocation")
                .bean(eCommerce, "askForDates")
                .bean(eCommerce, "askForNumberOfGuests")
                .bean(eCommerce, "askForHotel")
                .bean(eCommerce, "askForOptions")
                .bean(eCommerce, "displayReservationDetails")
                .bean(eCommerce, "showSuccessMessage(${body.toString()})");

        from("sjms2:topic:" + jmsPrefix + "cancellation")
                .log("cancellation notice ${body} ${headers}")
                .filter(header("vendorId").isEqualTo(vendorId))

                .unmarshal().json(CancelationNotice.class)
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        CancelationNotice notice = exchange.getMessage().getMandatoryBody(CancelationNotice.class);
                        exchange.getMessage().setHeaders(new HashMap<>());
                        exchange.getMessage().setHeader("to", notice.getEmail());
                        exchange.getMessage().setHeader("from", smtpFrom);
                        exchange.getMessage().setHeader("contentType", "text/html");
                        exchange.getMessage().setHeader("subject", "cancellation notice for venue");
                        exchange.getMessage().setBody("Dear Customer,\n\n Venue for your ticket " + notice.getTicketId() + " has been cancelled.\n\n Contact vendor for refund");
                    }
                })
                .log("cancellation notice ${body} ${headers}")
                .to("smtps:" + smtpHost + ":" + smtpPort + "?username=" + smtpUser + "&password=" + smtpPassword + "&contentType=")
                .bean(eCommerce, "showErrorMessage");


    }
}
