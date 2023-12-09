package fr.pantheonsorbonne.ufr27.miage.resources;


import fr.pantheonsorbonne.ufr27.miage.dto.Gig;
import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;
import fr.pantheonsorbonne.ufr27.miage.dto.RemainingQuota;
import fr.pantheonsorbonne.ufr27.miage.service.LocationService;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.Collection;

@Path("/hotelLocation")
public class BookingResource {

    @Inject
    protected LocationService service;

    @Path("locations")
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Collection<HotelLocation> getGigs() {
        Collection<HotelLocation> gig = service.getHotelLocations();
        if (gig.isEmpty()) {
            throw new WebApplicationException(404);
        } else {
            return gig;
        }
    }
}