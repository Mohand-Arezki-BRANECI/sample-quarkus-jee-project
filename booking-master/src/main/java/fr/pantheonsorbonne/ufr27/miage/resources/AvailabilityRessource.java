package fr.pantheonsorbonne.ufr27.miage.resources;

import fr.pantheonsorbonne.ufr27.miage.dto.Availability;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;
import fr.pantheonsorbonne.ufr27.miage.service.AvailabilityService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Path("/hotelAvailability")
public class AvailabilityRessource {
    @Inject
    protected AvailabilityService service;

    @Path("availability")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public List<Availability> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate) {
        System.out.println("TOTOTOT start---> ");

        List<Availability> availability = service.getConsistentlyAvailableHotels(numberOfGuests, startDate, endDate);
        System.out.println("TOTOTOT---> "+ availability);
        return availability;
    }
}




