package fr.pantheonsorbonne.ufr27.miage.dto;

public class HotelOption {

    private String name;

    private int hotelId;

    private double optionPrice;

    public HotelOption() {}
    public HotelOption(int hotelId, String name, double optionPrice) {
        this.hotelId = hotelId;
        this.optionPrice = optionPrice;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getHotelId() {
        return hotelId;
    }

    public double getOptionPrice() {
        return optionPrice;
    }

}
