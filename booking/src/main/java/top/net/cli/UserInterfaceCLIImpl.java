package top.net.cli;


import fr.pantheonsorbonne.ufr27.miage.dto.Booking;
import fr.pantheonsorbonne.ufr27.miage.dto.Gig;

import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import top.net.resource.LocationService;
import top.net.resource.VendorService;



import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;


@ApplicationScoped
public class UserInterfaceCLIImpl implements UserInterfaceCLI {

    @Inject
    @RestClient
    VendorService vendorService;

    @Inject
    @RestClient
    LocationService locationService;



    TextTerminal<?> terminal;
    TextIO textIO;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.vendorId")
    Integer vendorId;

    public void displayAvailableGigsToCli(){
        terminal.println("VendorId="+vendorId);
        for (Gig gig : vendorService.getGigs(vendorId)) {
            terminal.println("[" + gig.getVenueId() + "] " + gig.getArtistName() + " " + gig.getDate().format(DateTimeFormatter.ISO_DATE) + " " + gig.getLocation());
        }
    }


    public void askForHotelLocation() {
        terminal.println("Welcome to Booking");
        terminal.println("Please select from the location we provide here");
        for (HotelLocation hotelLocation : locationService.getHotelLocations()) {
            terminal.println("[" + hotelLocation.getLocationName() + "] " + hotelLocation.getLongitude() + " " + hotelLocation.getLatitude());
        }
        String hotelName = textIO.newStringInputReader().withPossibleValues(locationService.getHotelLocations().stream().map(g -> g.getLocationName()).collect(Collectors.toList())).read("Which location?");
    }

    public Booking getBookingFromOperator(){
        terminal.println("Which Gig to book?");

        Integer venueId = textIO.newIntInputReader().withPossibleValues(vendorService.getGigs(vendorId).stream().map(g -> g.getVenueId()).collect(Collectors.toList())).read("Which venue?");
        Integer sittingCount = textIO.newIntInputReader().read("How many seats?");
        Integer standingCount = textIO.newIntInputReader().read("How many pit tickets?");

        return new Booking(vendorId,venueId,standingCount,sittingCount);
    }

    @Override
    public void accept(TextIO textIO, RunnerData runnerData) {
        this.textIO = textIO;
        terminal = textIO.getTextTerminal();
   }

    @Override
    public void showErrorMessage(String errorMessage) {
        terminal.getProperties().setPromptColor(Color.RED);
        terminal.println(errorMessage);
        terminal.getProperties().setPromptColor(Color.white);
    }

    @Override
    public void showSuccessMessage(String s) {
        terminal.getProperties().setPromptColor(Color.GREEN);
        terminal.println(s);
        terminal.getProperties().setPromptColor(Color.white);
    }


    @Override
    public String getCustomerFirstName() {
        return this.textIO.newStringInputReader().read("Customer First Name");

    }

    @Override
    public String getCustomerLastName() {
        return this.textIO.newStringInputReader().read("Customer Last Name");

    }

    @Override
    public String getCustomerEmail() {
        return this.textIO.newStringInputReader().read("Customer Email");

    }



}
