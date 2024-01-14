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
import top.net.resource.*;
import top.net.resource.BankService;
import jakarta.ws.rs.core.Response;



import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    BankServiceAmerica bankServiceAmerica;

    @Inject
    @RestClient
    BankServiceIreland bankServiceIreland;




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

        terminal.println("this.startDate---->" + this.startDateString);
        terminal.println("this.endDate---->" + this.endDateString);
        terminal.println("Type de this.endDateString : " + this.endDateString.getClass().getName());


        for (Hotel hotel : availabilityService.getConsistentlyAvailableHotels(this.nbGuests, this.startDateString, this.endDateString)) {
            terminal.println("ajung aici");
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

    public void cancelReservation(){
        String reservationId = textIO.newStringInputReader().read("Which of your reservations would you like to cancel?");

        //TODO: Get reservation and associated Transaction

        String confirmation = textIO.newStringInputReader().read("Are you sure you want to cancel this reservation? [Y|N]");

        if(confirmation.equals("Y") || confirmation.equals("y")){
            // TODO: Also change Reservation status in Bookings Reservation as well as in Hotels Reservation Tables

            try {
                String bankNr  = textIO.newStringInputReader().read("Which Bank do you want to login to (Press the number)? \n 1) Bank of America \n 2) Bank of Ireland ");

                BankService bankServiceToUse;


                if(bankNr.equals("1")){
                    bankServiceToUse = bankServiceAmerica;
                }else{
                    bankServiceToUse = bankServiceIreland;
                }

                String email = textIO.newStringInputReader().read("Please insert your email associated with your account: ");
                String password = textIO.newStringInputReader().read("Please Insert your password:  ");

                // get Account information
                Response loginResponse = bankServiceToUse.loginToBankAccount(email, password);
                if (loginResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                    AccountDTO accountDTO = loginResponse.readEntity(AccountDTO.class);
                    // get the transferObject
                    Response bankTransferResponse = bankServiceToUse.getBankTransfer(reservationId,accountDTO.getAccountId(), accountDTO.getBankId());
                    if (loginResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                        TransactionDTO bankTransfer = bankTransferResponse.readEntity(TransactionDTO.class);

                        // construct inverse transaction
                        TransactionDTO transaction = new TransactionDTO("booking@booking.com","test",bankTransfer.getFromAccountId(), bankTransfer.getFromBankId(), bankTransfer.getToAccountId(),bankTransfer.getToBankId(),bankTransfer.getAmount(), "cancellation",reservationId);
                        Response transactionResponse = bankServiceAmerica.createTransaction(transaction);
                        this.showSuccessMessage(transactionResponse.readEntity(String.class));
                    }else {
                        // Handle the case where the login failed
                        String errorMessage = bankTransferResponse.readEntity(String.class);
                        showErrorMessage(errorMessage);
                    }
                }else {
                    // Handle the case where the login failed
                    String errorMessage = loginResponse.readEntity(String.class);
                    showErrorMessage("Login failed: " + errorMessage);
                }
            }catch (WebApplicationException e) {
                String respStr = e.getResponse().readEntity(String.class);
                this.showErrorMessage(respStr);
            }
        }
    }

    public void sendPayment(){
        terminal.println("We received the following transaction Request in your name from Booking: SHOW STUFF");
        terminal.println("To authorise the transaction pleas login to your MIAGE bank account!");

        String bankNr  = textIO.newStringInputReader().read("Which Bank do you want to login to (Press the number)? \n 1) Bank of America \n 2) Bank of Ireland ");

        BankService bankServiceToUse;

        if(bankNr.equals("1")){
            bankServiceToUse = bankServiceAmerica;
        }else{
            bankServiceToUse = bankServiceIreland;
        }


        String email = textIO.newStringInputReader().read("Please insert your email associated with your account: ");
        String password = textIO.newStringInputReader().read("Please Insert your password:  ");



        try {
            Response loginResponse = bankServiceToUse.loginToBankAccount(email, password);
            if (loginResponse.getStatus() == Response.Status.OK.getStatusCode()) {
                // Extract the serialized AccountDTO data from the response
                AccountDTO accountDTO = loginResponse.readEntity(AccountDTO.class);

                // Now 'accountDTO' contains the deserialized data
                // You can use the data as needed, for example, display it
                this.showSuccessMessage("Login successful. Welcome  " + accountDTO.getOwnerFirstName() + " " + accountDTO.getOwnerLastName());

                String confirmation = textIO.newStringInputReader().read("Would you like to confirm the payment? [Y/N]");
                if(confirmation.equals("Y") || confirmation.equals("y")){
                    //TODO:
                    TransactionDTO transaction = new TransactionDTO(email,password,2,1000, accountDTO.getAccountId(), accountDTO.getBankId(), 100, "clientPayment", "1");
                    terminal.println(transaction.getReservationId());
                    Response transactionResponse = bankServiceToUse.createTransaction(transaction);

                    this.showSuccessMessage(transactionResponse.readEntity(String.class));
                }
            } else {
                // Handle the case where the login failed
                String errorMessage = loginResponse.readEntity(String.class);
                showErrorMessage("Login failed: " + errorMessage);
            }

        } catch (WebApplicationException e) {
            String respStr = e.getResponse().readEntity(String.class);
            this.showErrorMessage(respStr);
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
