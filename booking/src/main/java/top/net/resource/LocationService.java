package top.net.resource;



import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import java.util.Collection;


@Path("/hotelLocation")
@RegisterRestClient(configKey = "location-api")
public interface LocationService {



    @Path("locations")
    @GET
    Collection<HotelLocation> getHotelLocations();
}
