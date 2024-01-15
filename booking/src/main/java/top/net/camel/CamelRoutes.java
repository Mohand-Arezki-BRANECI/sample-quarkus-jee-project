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
                .bean(eCommerce, "displayReservationDetails");

        from("sjms2:topic:hotelReservationResponse")
                .unmarshal().json(ReservationResponseDTO.class)
                .bean(eCommerce, "showSuccessMessage(${body.toString()})")
                .bean(eCommerce, "sendPayment");

        from("sjms2:topic:clientPaymentResponse")
                .bean(eCommerce,"showSuccessMessage('Your Reservation has been payed for and is reserved.')")
                .end();

        from("sjms2:topic:bookingPaymentResponse")
                .unmarshal().json(TransactionDTO.class)
                .bean(eCommerce,"showSuccessMessage('All done. The hotel received your payment. THanks for using our service. )")
                .end();


    }
}
