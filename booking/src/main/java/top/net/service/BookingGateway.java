package top.net.service;

public interface BookingGateway {
    void sendBookingOrder(int standingCount, int seatingCount, int venueId);
}
