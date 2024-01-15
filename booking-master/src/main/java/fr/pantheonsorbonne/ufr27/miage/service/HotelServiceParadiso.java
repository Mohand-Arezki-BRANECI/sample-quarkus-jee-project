package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/hotel")
@RegisterRestClient(configKey = "hotel-api-paradiso")
@ApplicationScoped
public interface HotelServiceParadiso {


    @Path("makeReservation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response makeReservation(ReservationRequestDTO reservationRequestDTO);


    // TODO
    @Path("checkRoomAvailability")
    @GET
    public Integer checkRoomAvailability(
                                    @QueryParam("startDate") String startDate,
                                    @QueryParam("endDate") String endDates,
                                    @QueryParam("nbrBeds") Integer nbrBeds);
}
