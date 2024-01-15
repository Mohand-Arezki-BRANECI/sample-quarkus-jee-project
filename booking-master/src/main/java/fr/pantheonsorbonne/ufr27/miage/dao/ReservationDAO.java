package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.HotelOption;
import fr.pantheonsorbonne.ufr27.miage.model.Reservation;
import fr.pantheonsorbonne.ufr27.miage.model.User;

import java.util.Set;

public interface ReservationDAO {
    public Hotel getHotelbyId(int hotelId);
    public Reservation save(ReservationRequestDTO reservationRequest, User reservationUser, Set<HotelOption> options, Hotel hotel);
}
