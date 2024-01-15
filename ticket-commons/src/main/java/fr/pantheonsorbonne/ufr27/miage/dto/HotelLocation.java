package fr.pantheonsorbonne.ufr27.miage.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class HotelLocation {
    private String locationName;
    private String longitude;
    private String latitude;

    // Constructor
    public HotelLocation(String locationName, String longitude, String latitude) {
        this.locationName = locationName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public HotelLocation(){};


    // Getter methods
    public String getLocationName() {
        return locationName;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    // Setter methods (if needed)
    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}