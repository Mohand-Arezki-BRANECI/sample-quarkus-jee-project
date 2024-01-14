package top.net.cli;

import org.beryx.textio.TextIO;

import java.text.ParseException;
import java.util.function.BiConsumer;

public interface UserInterfaceCLI extends BiConsumer<TextIO, RunnerData>, UserInterface {



    // booking-cli
    void displayAvailableGigsToCli();
    void askForHotelLocation();
    void askForDates() throws ParseException;
    void askForNumberOfGuests();
    void askForHotel();
    void askForOptions();

    // bank
    void sendPayment();
}
