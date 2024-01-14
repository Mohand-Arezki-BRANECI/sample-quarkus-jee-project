package fr.pantheonsorbonne.ufr27.miage.dto;

import java.util.Date;

public class Availability {

    private int bedsNumber;
    private int numberFreeRooms;
    private Date date ;
    private Hotel hotel;

    private Double price;

    public Availability() {}

    public Availability(Hotel hotel, double price) {
        this.hotel = hotel ;
        this.price = price;
    }
    public Availability(int bedsNumber,int numberFreeRooms,  Hotel hotel, Date date, Double price) {

        this.bedsNumber =bedsNumber;
        this.numberFreeRooms =numberFreeRooms;
        this.hotel = hotel ;
        this.date = date;
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }
    public Date getDate(){return date;}

    public Double getPrice(){return price;}
    public int getNumberFreeRooms(){return numberFreeRooms;}
    public int getBedsNumber(){return bedsNumber;}


}
