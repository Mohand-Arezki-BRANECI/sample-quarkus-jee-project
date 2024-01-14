package top.net.resource;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/hotel")
@RegisterRestClient(configKey = "hotel-api")
public class HotelService {

    // TODO
    @Path("createReservation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(/* ReservationDTO reservation*/) {
        return null;
    }

    // TODO
    @Path("checkRoomAvailability")
    @GET
    public Integer checkRoomAvailability(/*
                                    @QueryParam("startDate") String startDate,
                                    @QueryParam("endDate") String endDates,
                                    @QueryParam("nbrBeds") Integer nbrBeds,*/) {
        return null;
    }


}
