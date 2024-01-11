package fr.pantheonsorbonne.ufr27.miage.dto;

import java.util.Date;

public class Availability {

    private int bedsNumber;
    private int numberFreeRooms;
    private Date date ;
    private Hotel hotel;
    public Availability(int bedsNumber,int numberFreeRooms,  Hotel hotel, Date date) {

        this.bedsNumber =bedsNumber;
        this.numberFreeRooms =numberFreeRooms;
        this.hotel = hotel ;
        this.date = date;
    }
    public Hotel getHotel() {
        return hotel;
    }
    public Date getDate(){return date;}
    public int getNumberFreeRooms(){return numberFreeRooms;}
    public int getBedsNumber(){return bedsNumber;}


}
