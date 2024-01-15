package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.ReservationDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.*;
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


        Reservation returnReservation = reservationDAO.save(reservation, reservationUser, options, hotel );
        hotelRequest.setBookingReservationId(String.valueOf(returnReservation.getReservationNumber()));

        try (ProducerTemplate producer = context.createProducerTemplate()) {
            producer.sendBodyAndHeader("direct:sendToHotel", hotelRequest, "toWhichHotel", hotel.getHotelName());
        } catch (IOException e) {
            e.printStackTrace();
        }


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
        hotelPayment.setEmail("booking@booking.com");
        hotelPayment.setPassword("test");


        if(reservation.getHotel().getHotelName().equals("Hotel Paradiso")){
            hotelPayment.setToBankId(999);
            hotelPayment.setToAccountId(2);

        }else if(reservation.getHotel().getHotelName().equals("Hotel California")){
            hotelPayment.setToBankId(1000);
            hotelPayment.setToAccountId(3);

        }

        //booking pays hotel
        bankServiceAmerica.createTransaction(hotelPayment);
        return reservation.getHotel().getHotelName();
    }

    @Override
    public void upateReservationStatus(TransactionDTO transactionDTO){
        Reservation reservation = reservationDAO.getReservationById(transactionDTO.getReservationId());

        String reservationId = transactionDTO.getReservationId();

        String statusPayed = "CONFIRMED";

        UpdateReservationDTO updatedStuff = null;

        if(reservation.getHotel().getHotelName().equals("Hotel Paradiso")){
            updatedStuff = hotelServiceParadiso.update_reservation(statusPayed, reservationId);
        }else if(reservation.getHotel().getHotelName().equals("Hotel California")){
            updatedStuff = hotelServiceCalifornia.update_reservation(statusPayed, reservationId);
        }

        assert updatedStuff != null;
        reservationDAO.changeStatus(updatedStuff.getBookingReservationId(), updatedStuff.getReservationStatus());

    }
}
