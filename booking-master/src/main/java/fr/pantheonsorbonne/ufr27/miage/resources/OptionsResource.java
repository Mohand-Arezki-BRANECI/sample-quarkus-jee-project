package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dto.HotelOption;
import fr.pantheonsorbonne.ufr27.miage.service.LocationService;
import fr.pantheonsorbonne.ufr27.miage.service.OptionsService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import org.apache.http.ParseException;

import java.util.List;
@Path("/hotelOptions")

public class OptionsResource {

    @Inject
    protected OptionsService service;

    @Path("options")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<HotelOption> getHotelOptions(@QueryParam("hotelId") int hotelId){
        try {
            return service.getHotelOptions(hotelId);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
