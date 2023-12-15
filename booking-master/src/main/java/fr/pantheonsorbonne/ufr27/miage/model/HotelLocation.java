package fr.pantheonsorbonne.ufr27.miage.model;

import jakarta.persistence.*;

@NamedQueries(
        {
                @NamedQuery(name = "getAllHotelLocations", query = "SELECT h from HotelLocation h"),

        }
)
@Table(name = "HotelLocation")
@Entity
public class HotelLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "locationId", nullable = false)
    private Integer id;
    @Column(name = "locationName", nullable = false, length = 45)
    private String locationName;
    @Column(name = "latitude", nullable = false, length = 45)
    private String latitude;
    @Column(name = "longitude", nullable = false, length = 45)
    private String longitude;

    public String getLocationName() {
        return locationName;
    }
    public void setLocationName(String name) {
        this.locationName = name;
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String langitude) {
        this.latitude = langitude;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
