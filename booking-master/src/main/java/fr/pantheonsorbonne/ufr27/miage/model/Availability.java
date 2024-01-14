package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

import java.util.Date;

@Table(name = "Availability")
@Entity
@NamedQueries({
        @NamedQuery(
                name = "findByDateAndGuests",
                query ="SELECT a FROM Availability a " +
                        "WHERE a.date = :date " +
                        "AND a.bedsNumber = :numberOfGuests"
        ),
})
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availabilityId", nullable = false)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;
    @Column(name = "numberFreeRooms")
    private Integer numberFreeRooms;

    @Column(name = "date")
    private Date date;

    @Column(name = "bedsNumber")
    private Integer bedsNumber;

    @Column(name = "roomPrice")
    private Double roomPrice;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberFreeRooms() {
        return numberFreeRooms;
    }
    public void setNumberFreeRooms(Integer numberFreeRooms) {
        this.numberFreeRooms = numberFreeRooms;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getBedsNumber() {
        return bedsNumber;
    }
    public void setBedsNumber(Integer bedsNumber) {
        this.bedsNumber = bedsNumber;
    }

    public Double getRoomPrice() {
        return roomPrice;
    }
    public void setRoomPrice(Double roomPrice) {
        this.roomPrice = roomPrice;
    }

}
