package top.net.resource;


import fr.pantheonsorbonne.ufr27.miage.dto.Availability;
import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Date;
import java.util.List;
@Path("/hotelAvailability")
@RegisterRestClient(configKey = "location-api")
public interface AvailabilityService {

    @Path("availability")
    @GET
    List<Availability> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate);

}
