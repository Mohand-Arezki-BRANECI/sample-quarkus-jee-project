package top.net.cli;

import fr.pantheonsorbonne.ufr27.miage.dto.Booking;
import jakarta.inject.Inject;
import org.beryx.textio.TextIO;
import org.beryx.textio.TextIoFactory;
import org.beryx.textio.swing.SwingTextTerminal;
import picocli.CommandLine.Command;
import top.net.service.BookingGateway;




@Command(name = "greeting", mixinStandardHelpOptions = true)
public class Main implements Runnable {


    @Inject
    UserInterfaceCLI eCommerce;

    @Inject
    BookingGateway bookingGateway;

    @Override
    public void run() {

        System.setProperty(TextIoFactory.TEXT_TERMINAL_CLASS_PROPERTY, SwingTextTerminal.class.getName());
        TextIO textIO = TextIoFactory.getTextIO();
        var terminal = TextIoFactory.getTextTerminal();

        eCommerce.accept(textIO, new RunnerData(""));


        while (true) {
            try {

                // booking-cli
                eCommerce.askToLogIn();

                /*
                eCommerce.askForHotelLocation();
                eCommerce.askForDates();
                eCommerce.askForNumberOfGuests();
                eCommerce.askForHotel();
                eCommerce.askForOptions();
                eCommerce.displayReservationDetails();



                // bank
                eCommerce.sendPayment();
                eCommerce.cancelReservation();

                 */

            } catch (Exception e) {
                eCommerce.showErrorMessage(e.getMessage());
            }
        }


    }

}
