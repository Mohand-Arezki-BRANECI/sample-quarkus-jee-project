package top.net.cli;


import fr.pantheonsorbonne.ufr27.miage.dto.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MultivaluedMap;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextTerminal;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import top.net.resource.AvailabilityService;
import top.net.resource.BankService;
import top.net.resource.LocationService;
import top.net.resource.VendorService;
import jakarta.ws.rs.core.Response;



import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@ApplicationScoped
public class UserInterfaceCLIImpl implements UserInterfaceCLI {

    @Inject
    @RestClient
    VendorService vendorService;

    @Inject
    @RestClient
    LocationService locationService;

    @Inject
    @RestClient
    AvailabilityService availabilityService;


    @Inject
    @RestClient
    BankService bankService;


    TextTerminal<?> terminal;
    TextIO textIO;

    @ConfigProperty(name = "fr.pantheonsorbonne.ufr27.miage.vendorId")
    Integer vendorId;
    private String startDateString;
    private String endDateString;
    private int nbGuests;
    public void displayAvailableGigsToCli() {
        terminal.println("VendorId=" + vendorId);
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


    public void askForHotel(){
        terminal.print("!!!");
        terminal.println("find hotel for you !!!"+  this.nbGuests);
        terminal.println("this.nbGuests" + this.nbGuests);
        terminal.println("this.startDate---->" + this.startDateString);
        terminal.println("this.endDate---->" + this.endDateString);

        for (Hotel hotel : availabilityService.getConsistentlyAvailableHotels(this.nbGuests, this.startDateString, this.endDateString)) {
            terminal.println("[" + hotel.getHotelName()+ "] " );
        }
    }
    public void askForNumberOfGuests() {
        this.nbGuests = textIO.newIntInputReader().read("How many guests?");

    }

    public void askForDates() throws ParseException {

        this.startDateString = textIO.newStringInputReader().read("Specify start date (yyyy-MM-dd) : ");
        this.endDateString = textIO.newStringInputReader().read("Specify end date (yyyy-MM-dd) : ");

        if (!isValidDateFormat(this.startDateString) || !isValidDateFormat(this.endDateString)) {
            terminal.println("Veuillez entrer des dates valides au format yyyy-MM-dd.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
             Date startDate = dateFormat.parse(this.startDateString);
            Date endDate = dateFormat.parse(this.endDateString);
            Date today = new Date();

            if (startDate.before(today)) {
                terminal.println("La date de début ne peut pas être dans le passe.");
                return;
            }

            if (endDate.before(startDate)) {
                terminal.println("La date de fin ne peut pas être antérieure à la date de début.");
                return;
            }


    }catch (ParseException e) {
            terminal.println("Une erreur s'est produite lors de l'analyse des dates.");
            e.printStackTrace();
        }

    }

    private boolean isValidDateFormat(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);

        try {
            dateFormat.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }


    public Booking getBookingFromOperator(){
        terminal.println("Which Gig to book?");

        Integer venueId = textIO.newIntInputReader().withPossibleValues(vendorService.getGigs(vendorId).stream().map(g -> g.getVenueId()).collect(Collectors.toList())).read("Which venue?");
        Integer sittingCount = textIO.newIntInputReader().read("How many seats?");
        Integer standingCount = textIO.newIntInputReader().read("How many pit tickets?");

        return new Booking(vendorId,venueId,standingCount,sittingCount);
    }

    public void sendPayment(){
        terminal.println("We received the following transaction Request in your name from Booking: SHOW STUFF");
        terminal.println("To authorise the transaction pleas login to your MIAGE bank account!");

        String email = textIO.newStringInputReader().read("Please insert your email associated with your account: ");
        String password = textIO.newStringInputReader().read("Please Insert your password:  ");

        try {
            Response loginResponse = bankService.loginToBankAccount(email, password);
            if (loginResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                // Extract the serialized AccountDTO data from the response
                AccountDTO accountDTO = loginResponse.readEntity(AccountDTO.class);

                // Now 'accountDTO' contains the deserialized data
                // You can use the data as needed, for example, display it
                this.showSuccessMessage("Login successful. Welcome  " + accountDTO.getOwnerFirstName() + " " + accountDTO.getOwnerLastName());

                String confirmation = textIO.newStringInputReader().read("Would you like to confirm the payment? [Y/N]");
                if(confirmation.equals("Y")){
                    //TODO:
                    TransactionDTO transaction = new TransactionDTO(email,password,1,999,1,1000,100);
                    Response transactionResponse = bankService.createTransaction(transaction);
                    this.showSuccessMessage(transactionResponse.readEntity(String.class));
                }
            } else {
                // Handle the case where the login failed
                String errorMessage = loginResponse.readEntity(String.class);
                showErrorMessage("Login failed. " + errorMessage);
            }

        } catch (WebApplicationException e) {
            String respStr = e.getResponse().readEntity(String.class);
            terminal.println(respStr);
        }

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
