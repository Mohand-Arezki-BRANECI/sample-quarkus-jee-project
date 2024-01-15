package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Reservation;
import jakarta.enterprise.context.RequestScoped;


public interface ReservationService {
    Reservation addReservation(ReservationRequestDTO reservation, int hotelId);
}
