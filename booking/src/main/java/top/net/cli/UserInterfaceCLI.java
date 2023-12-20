package top.net.cli;

import org.beryx.textio.TextIO;

import java.text.ParseException;
import java.util.function.BiConsumer;

public interface UserInterfaceCLI extends BiConsumer<TextIO, RunnerData>, UserInterface {

    void displayAvailableGigsToCli();

    void askForHotelLocation();

    // booking-cli
    void askForDates() throws ParseException;

    // bank
    void sendPayment();
}
