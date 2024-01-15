package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.BookingReservationDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Reservation;
import jakarta.enterprise.context.RequestScoped;


public interface ReservationService {
    Reservation addReservation(BookingReservationDTO reservation, int hotelId);

    String sendPaymentToHotel(TransactionDTO transactionDTO);

}
