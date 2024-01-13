package fr.pantheonsorbonne.ufr27.miage.dto;

public class Hotel {

    private String hotelName;
    private int locationId;
    public Hotel() {}
    public Hotel(String hotelName) {
        this.hotelName = hotelName;
    }

    public Hotel(String hotelName, int locationId) {
        this.hotelName = hotelName;
    }
    public String getHotelName() {
        return hotelName;
    }

    public int getLocationId() {
        return locationId;
    }



}
