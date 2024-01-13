package top.net.resource;


import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import java.util.List;

@Path("/hotelAvailability")
@RegisterRestClient(configKey = "location-api")
public interface AvailabilityService {

    @Path("availability")
    @GET
    List<Hotel> getConsistentlyAvailableHotels(@QueryParam("numberOfGuests") int numberOfGuests,
                                               @QueryParam("startDate") String startDate,
                                               @QueryParam("endDate") String endDate,
                                               @QueryParam("locationId") int locationId);

}
