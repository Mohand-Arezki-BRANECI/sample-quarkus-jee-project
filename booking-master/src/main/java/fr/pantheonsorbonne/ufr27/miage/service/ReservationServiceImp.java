package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.ReservationDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.BookingReservationDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.UserDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.HotelOption;
import fr.pantheonsorbonne.ufr27.miage.model.Reservation;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationServiceImp implements ReservationService {

    @Inject
    ReservationDAO reservationDAO;

    @Inject
    OptionsService optionService;

    @Inject
    LoginService loginService;

    @Inject
    @RestClient
    BankServiceAmerica bankServiceAmerica;

    @Inject
    @RestClient
    BankServiceIreland bankServiceIreland;

    @Inject
    @RestClient
    HotelServiceParadiso hotelServiceParadiso;

    @Inject
    @RestClient
    HotelServiceCalifornia hotelServiceCalifornia;


    @Inject
    CamelContext context;
    @Override
    public Reservation addReservation(BookingReservationDTO reservation, int hotelId) {
        User reservationUser = loginService.getByEmail(reservation.getUser().getEmail());

        Set<HotelOption> options = optionService.getHotelOptionsModel(hotelId).stream().filter(option -> reservation.getOptionsNames().contains(option.getOptionName())).collect(Collectors.toSet());

        Hotel hotel = reservationDAO.getHotelbyId(hotelId);

        UserDTO hotelUser = new UserDTO();
        hotelUser.setEmailAddress(reservation.getUser().getEmail());
        hotelUser.setName(reservation.getUser().getFirstName());
        hotelUser.setLastName(reservation.getUser().getLastName());
        hotelUser.setPhoneNumber(null);

        ReservationRequestDTO hotelRequest = new ReservationRequestDTO();
        hotelRequest.setUser(hotelUser);
        hotelRequest.setFrom(reservation.getFrom());
        hotelRequest.setTo(reservation.getTo());
        hotelRequest.setGuests(reservation.getGuests());
        hotelRequest.setOptionsNames(reservation.getOptionsNames());


        try (ProducerTemplate producer = context.createProducerTemplate()) {
            producer.sendBodyAndHeader("direct:sendToHotel", hotelRequest, "toWhichHotel", hotel.getHotelName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reservation returnReservation = reservationDAO.save(reservation, reservationUser, options, hotel );
        return  returnReservation;
    }

    @Override
    public String sendPaymentToHotel(TransactionDTO transactionDTO) {
        Reservation reservation = reservationDAO.getReservationById(transactionDTO.getReservationId());

        TransactionDTO hotelPayment = new TransactionDTO();
        hotelPayment.setFromBankId(1000);
        hotelPayment.setFromAccountId(2);
        hotelPayment.setReservationId(String.valueOf(reservation.getReservationNumber()));
        //this is the gain of booking
        hotelPayment.setAmount(transactionDTO.getAmount()-100);
        hotelPayment.setTransactionPurpose("bookingPayment");

        if(reservation.getHotel().getHotelName().equals("Hotel Paradiso")){
            hotelPayment.setToBankId(999);
            hotelPayment.setToAccountId(2);

            bankServiceAmerica.createTransaction(hotelPayment);
            //TODO change Status
        }else if(reservation.getHotel().getHotelName().equals("Hotel California")){
            hotelPayment.setToBankId(1000);
            hotelPayment.setToAccountId(3);
            bankServiceAmerica.createTransaction(hotelPayment);
            //TODO change Status
        }
        return reservation.getHotel().getHotelName();
    }
}
