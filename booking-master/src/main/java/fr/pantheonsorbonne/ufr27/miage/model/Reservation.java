package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

import java.util.Date;

@Table(name = "Reservation")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReservation", nullable = false)
    private Integer id;

    @Column(name = "number_Guests", nullable = false)
    private Integer guestsNumber;
    @Column(name = "booked_date", nullable = false)
    private Date bookingDate;
    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "status", nullable = false)
    private String status ;

    @Column(name = "hotel_id", nullable = false)
    private Integer hotelId ;

    @Column(name = "reservetionRoom_id", nullable = false)
    private Integer reservetionRoomId ;

    public Integer getGuestsNumber() {
        return guestsNumber;
    }

    public void setGuestsNumber(Integer number) {
        this.guestsNumber = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
