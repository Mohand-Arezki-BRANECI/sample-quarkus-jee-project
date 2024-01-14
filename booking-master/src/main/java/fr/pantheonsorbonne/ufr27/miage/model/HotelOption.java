package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

import java.util.Set;

@NamedQueries({
        @NamedQuery(name = "findHotelOptions",
                query = "SELECT h FROM HotelOption h ")
})

@Table(name = "HotelOption")
@Entity
public class HotelOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionId", nullable = false)
    private Integer id;

    @Column(name = "optionName")
    private String optionName;

    @Column(name = "optionPrice")
    private Double optionPrice;

    @ManyToMany(mappedBy="options")
    private Set<Reservation> reservations;

    @ManyToOne
    @JoinColumn(name = "hotelId")
    private Hotel hotel;


    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
    public Double getOptionPrice() {
        return optionPrice;
    }
    public void setOptionPrice(Double optionPrice) {
        this.optionPrice = optionPrice;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(Set<Reservation> reservations) {
        this.reservations= reservations;
    }

    @Column(name = "optionPrice")
    private Double optionPrice;

    public String getOptionName() {
        return optionName;
    }
    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getOptionPrice() {
        return optionPrice;
    }

    public void setOptionPrice(Double optionPrice) {
        this.optionPrice = optionPrice;
    }
    
    public Hotel getHotel() {
        return hotel;
    }
    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }
}