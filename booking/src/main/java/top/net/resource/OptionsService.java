package top.net.resource;

import fr.pantheonsorbonne.ufr27.miage.dto.HotelOption;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/hotelOptions")
@RegisterRestClient(configKey = "location-api")
public interface OptionsService {

    @Path("options")
    @GET
    List<HotelOption> getHotelOptions(@QueryParam("hotelId") int hotelId);
}
