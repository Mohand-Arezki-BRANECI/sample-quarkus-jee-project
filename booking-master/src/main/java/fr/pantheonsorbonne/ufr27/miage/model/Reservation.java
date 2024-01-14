package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Set;

@Table(name = "Reservation")
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReservation", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
    @ManyToMany
    @JoinTable(name="ReservationOptions")
    private Set<HotelOption> options;
    public Set<HotelOption> getOptions() {
        return options;
    }
    public void setOptions(Set<HotelOption> options) {
        this.options= options;
    }

    @Column(name = "reservationNumber", nullable = false)
    private Integer reservationNumber ;
    @Column(name = "status", nullable = false)
    private String status ;
    @Column(name = "startDate", nullable = false)
    private Date startDate;
    @Column(name = "endDate", nullable = false)
    private Date endDate;
    @Column(name = "bedsNumber", nullable = false)
    private Integer bedsNumber;
    @Column(name = "bookingDate")
    private Date bookingDate;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReservationNumber() {return reservationNumber;}
    public void setReservationNumber(Integer reservationNumber) {this.reservationNumber = reservationNumber;}
    public String getStatus(){return status;}
    public void setStatus(String status) {this.status = status;}
    public Date getStartDate(){return startDate;}
    public void setStartDate(Date startDate) {this.startDate = startDate;}
    public Date getEndDate(){return endDate;}
    public void setEndDate(Date endDate) {this.endDate = endDate;}
    public Integer getBedsNumber() {return bedsNumber;}
    public void setBedsNumber(Integer bedsNumber){this.bedsNumber = bedsNumber;}
    public Date getBookingDate() {return bookingDate;}
    public void setBookingDate(Date bookingDate){this.bookingDate = bookingDate;}
    public User getUser() {return user;}
    public void setUser(User user){this.user = user;}

}
