package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationResponseDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.UpdateReservationDTO;
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


    @Path("make_reservation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public ReservationResponseDTO make_reservation(ReservationRequestDTO reservationRequestDTO);


    @Path("update_reservation")
    @PUT
    public UpdateReservationDTO update_reservation(@QueryParam("reservationStatus") String reservationStatus,
                                                   @QueryParam("reservationNumber") String reservationNumber);
}
