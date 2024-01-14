package fr.pantheonsorbonne.ufr27.miage.dto;

public class Hotel {

    private String hotelName;
    private int id;
    public Hotel() {}
    public Hotel(String hotelName) {
        this.hotelName = hotelName;
    }

    public Hotel(String hotelName, int id) {
        this.hotelName = hotelName;
        this.id = id;
    }
    public String getHotelName() {
        return hotelName;
    }

    public int getId() {
        return id;
    }



}
