package fr.pantheonsorbonne.ufr27.miage.camel;


import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.service.HotelServiceCalifornia;
import fr.pantheonsorbonne.ufr27.miage.service.HotelServiceParadiso;
import jakarta.inject.Named;
import org.apache.camel.CamelContext;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.rest.client.inject.RestClient;

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
                .log(LoggingLevel.INFO,"Booking received the following payment: ${body}");


        from("direct:sendToHotel")
                .log("${body}")
                .choice()
                    .when(header("toWhichHotel").isEqualTo("Hotel Paradiso"))
                        .bean(hotelServiceParadiso, "makeReservation")
                        .to("direct:handleCreateReservationResponse")
                    .when(header("toWhichHotel").isEqualTo("Hotel California"))
                        .bean(hotelServiceCalifornia, "makeReservation")
                        .to("direct:handleCreateReservationResponse")
                .endChoice();

        from("direct:handleCreateReservationResponse")
                .log("${body}");

}
}
