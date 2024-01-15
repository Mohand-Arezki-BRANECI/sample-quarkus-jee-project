package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Reservation;
import fr.pantheonsorbonne.ufr27.miage.service.ReservationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/reservation")
@RegisterRestClient(configKey = "location-api")
public class ReservationRessource {

    @Inject
    ReservationService service;

    @Path("createReservation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(@QueryParam("hotelId") int hotelId, ReservationRequestDTO reservation){

        Reservation newReservation = service.addReservation(reservation, hotelId);

        // TODO: send to hotel to make reservation
        // String responseMessage = transactionGateway.makeTransaction(transaction);

        return Response.status(Response.Status.OK)
                .entity(newReservation)
                .build();
    }
}
