package top.net.cli;

import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import org.beryx.textio.TextIO;

import java.text.ParseException;
import java.util.function.BiConsumer;

public interface UserInterfaceCLI extends BiConsumer<TextIO, RunnerData>, UserInterface {



    // booking-cli
    void askForHotelLocation();
    void askForDates() throws ParseException;
    void askForNumberOfGuests();
    void askForHotel();
    void askForOptions();
    void askToLogIn();

    void displayReservationDetails();
    // bank
    void sendPayment();

    void cancelReservation();
}
