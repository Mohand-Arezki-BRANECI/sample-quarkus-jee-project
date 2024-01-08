package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import fr.pantheonsorbonne.ufr27.miage.service.AvailabilityService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Path("/hotelAvailability")
public class AvailabilityRessource {


    @Inject
    protected AvailabilityService service;

    @Path("availability")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<Hotel> getConsistentlyAvailableHotels(
            @QueryParam("numberOfGuests") int numberOfGuests,
            @QueryParam("startDate") String startDate,
            @QueryParam("endDate") String endDate) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            Date start = dateFormat.parse(startDate);
            Date end = dateFormat.parse(endDate);
            return service.getConsistentlyAvailableHotels(numberOfGuests, start, end);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}




