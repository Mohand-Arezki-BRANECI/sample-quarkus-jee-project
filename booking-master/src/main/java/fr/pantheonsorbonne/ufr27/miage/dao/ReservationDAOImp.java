package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.dto.BookingReservationDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.ReservationRequestDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.dto.UpdateReservationDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.HotelOption;
import fr.pantheonsorbonne.ufr27.miage.model.Reservation;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@ApplicationScoped
public class ReservationDAOImp implements ReservationDAO {

    @Inject
    private EntityManager entityManager;

    @Override
    @Transactional
    public Reservation getReservationById(String reservationId){
        String jpql = "SELECT r FROM Reservation r WHERE r.reservationNumber = :reservationId";

        TypedQuery<Reservation> query = entityManager.createQuery(jpql, Reservation.class);
        query.setParameter("reservationId", Integer.parseInt(reservationId));

        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }

    }

    @Override
    @Transactional
    public void changeStatus(String reservationID, String status) {
        Reservation res = null;
        res = entityManager.createQuery("select res from Reservation res where res.reservationNumber = :reservationNumber", Reservation.class).setParameter("reservationNumber", reservationID).getSingleResult();
        if (res != null) {
            entityManager.createQuery("update Reservation r set r.status =:reservationStatus where r.reservationNumber = :reservationNumber")
                    .setParameter("reservationStatus", status)
                    .setParameter("reservationNumber", reservationID).executeUpdate();
        }
    }


    @Override
        @Transactional
        public Reservation save(BookingReservationDTO reservation, User reservationUser, Set<HotelOption> options, Hotel hotel){
            Reservation newReservation = new Reservation();
            newReservation.setHotel(hotel);
            newReservation.setUser(reservationUser);
            newReservation.setStartDate(Date.from(reservation.getFrom().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newReservation.setEndDate(Date.from(reservation.getTo().atStartOfDay(ZoneId.systemDefault()).toInstant()));
            newReservation.setBedsNumber(reservation.getGuests());
            newReservation.setOptions(options);
            newReservation.setStatus("PENDING");
            newReservation.setBookingDate(null);
            newReservation.setReservationNumber(UUID.randomUUID().hashCode());

            entityManager.persist(newReservation);

            return newReservation;
        }

        @Override
        @Transactional
        public Hotel getHotelbyId(int hotelId){
            String jpql = "SELECT h FROM Hotel h WHERE h.id = :hotelId";

            TypedQuery<Hotel> query = entityManager.createQuery(jpql, Hotel.class);
            query.setParameter("hotelId", hotelId);

            try {
                return query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        }


}
