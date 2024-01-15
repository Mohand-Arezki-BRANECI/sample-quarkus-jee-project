package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dto.CancelationNotice;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationResponseDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.service.HotelServiceCalifornia;
import fr.pantheonsorbonne.ufr27.miage.service.HotelServiceParadiso;
import fr.pantheonsorbonne.ufr27.miage.service.ReservationService;
import jakarta.inject.Named;
import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.HashMap;

import static net.bytebuddy.implementation.MethodDelegation.to;

@ApplicationScoped
public class CamelRoutes extends RouteBuilder {

    @ConfigProperty(name = "camel.routes.enabled", defaultValue = "true")
    boolean isRouteEnabled;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.jmsPrefix")
    String jmsPrefix;

    @Inject
    CamelContext camelContext;
    @Inject
    @RestClient
    HotelServiceParadiso hotelServiceParadiso;

    @Inject
    @RestClient
    HotelServiceCalifornia hotelServiceCalifornia;

    @Override
    public void configure() throws Exception {

        from("sjms2:topic:clientPaymentResponse")
                .unmarshal().json(TransactionDTO.class)
                .log(LoggingLevel.INFO,"Booking received the following payment: ${body}")
                    .bean(ReservationService.class, "sendPaymentToHotel");


        from("sjms2:topic:bookingPaymentResponse")
                .unmarshal().json(TransactionDTO.class)
                .log(LoggingLevel.INFO,"Booking payed the following payment: ${body}")
                        .bean(ReservationService.class, "upateReservationStatus")
                                .to("sjms2:topic:clientPaymentResponse");



        from("direct:sendToHotel")
                .log("${body}")
                .choice()
                    .when(header("toWhichHotel").isEqualTo("Hotel Paradiso"))
                        .bean(hotelServiceParadiso, "make_reservation")
                        .to("direct:handleCreateReservationResponse")
                    .when(header("toWhichHotel").isEqualTo("Hotel California"))
                        .bean(hotelServiceCalifornia, "make_reservation")
                        .to("direct:handleCreateReservationResponse")
                .endChoice();

        from("direct:handleCreateReservationResponse")
                .process(new Processor() {
                    @Override
                    public void process(Exchange exchange) throws Exception {

                        ReservationResponseDTO reservationResponse = exchange.getMessage().getMandatoryBody(ReservationResponseDTO.class);
                        exchange.getMessage().setBody(reservationResponse);
                    }
                })
                .log("${body}")
                .marshal().json()
                .to("sjms2:topic:hotelReservationResponse");



}
}
