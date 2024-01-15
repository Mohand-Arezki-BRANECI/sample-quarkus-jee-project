package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.ReservationDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.HotelOption;
import fr.pantheonsorbonne.ufr27.miage.model.Reservation;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@RequestScoped
public class ReservationServiceImp implements ReservationService {

    @Inject
    ReservationDAO reservationDAO;

    @Inject
    OptionsService optionService;

    @Inject
    LoginService loginService;

    @Inject
    CamelContext context;
    @Override
    public Reservation addReservation(ReservationRequestDTO reservation, int hotelId) {
        User reservationUser = loginService.getByEmail(reservation.getUser().getEmail());

        Set<HotelOption> options = optionService.getHotelOptionsModel(hotelId).stream().filter(option -> reservation.getOptionsNames().contains(option.getOptionName())).collect(Collectors.toSet());

        Hotel hotel = reservationDAO.getHotelbyId(hotelId);

        try (ProducerTemplate producer = context.createProducerTemplate()) {
            producer.sendBodyAndHeader("direct:sendToHotel", reservation, "toWhichHotel", hotel.getHotelName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        Reservation returnReservation = reservationDAO.save(reservation, reservationUser, options, hotel );
        return  returnReservation;
    }
}
