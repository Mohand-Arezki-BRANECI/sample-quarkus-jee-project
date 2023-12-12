package fr.pantheonsorbonne.ufr27.miage.model;
import jakarta.persistence.*;

@Table(name = "Hotel")
@Entity

public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotelId", nullable = false)
    private Integer id;
    @Column(name = "hotelName", nullable = false, length = 45)
    private String hotelName;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getHotelName() {
        return hotelName;
    }
    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }




}
