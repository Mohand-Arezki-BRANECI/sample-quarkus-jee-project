package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

import java.util.Set;

@Table(name = "HotelOption")
@Entity
public class HotelOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "optionId", nullable = false)
    private Integer id;

    @ManyToMany(mappedBy="options")
    private Set<Hotel> hotels;
    public Set<Hotel> getHotels() {
        return hotels;
    }
    public void setHotels(Set<Hotel> hotels) {
        this.hotels= hotels;
    }

    @ManyToMany(mappedBy="options")
    private Set<Reservation> reservations;
    public Set<Reservation> getReservations() {
        return reservations;
    }
    public void setReservations(Set<Reservation> reservations) {
        this.reservations= reservations;
    }
    @Column(name = "optionName")
    private String optionName;

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
}