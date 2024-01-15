package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/hotel")
@RegisterRestClient(configKey = "hotel-api")
public interface HotelService {


    @Path("makeReservation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(ReservationRequestDTO reservationRequestDTO);


    // TODO
    @Path("checkRoomAvailability")
    @GET
    public Integer checkRoomAvailability(
                                    @QueryParam("startDate") String startDate,
                                    @QueryParam("endDate") String endDates,
                                    @QueryParam("nbrBeds") Integer nbrBeds);
}
