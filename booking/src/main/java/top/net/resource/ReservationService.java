package top.net.resource;

import fr.pantheonsorbonne.ufr27.miage.dto.BookingReservationDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/reservation")
@RegisterRestClient(configKey = "location-api")
public interface ReservationService {

    @Path("createReservation")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(@QueryParam("hotelId") int hotelId, BookingReservationDTO reservation);
}
