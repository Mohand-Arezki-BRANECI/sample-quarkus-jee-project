package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.AvailabilityDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
public class AvailabilityServiceImp implements AvailabilityService  {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    AvailabilityDAO availabilityDAO;
    @Override
    public List<Hotel> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate) {
        ArrayList<Hotel> availableHotels = new ArrayList<>();

      List initialAvailableHotels = availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate);

        /*List<Availability> initialAvailableHotels = availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate);
        availableHotels.addAll(initialAvailableHotels); */


        for (Object initAv : initialAvailableHotels) {
            if (initAv instanceof Availability availability) {
                Hotel hotel = new Hotel(availability.getHotel().getHotelName());
                // Your further processing with the 'hotel' object
                availableHotels.add(hotel);
            }

            //availableHotels.add(new fr.pantheonsorbonne.ufr27.miage.dto.Availability(initAv.getBedsNumber(), initAv.getNumberFreeRooms(), hotel, initAv.getDate()));

        }
        Date currentDate = new Date(startDate.getTime());
        while (!currentDate.after(endDate)) {
            List<Hotel> currentAvailableHotels = new ArrayList<>();
            for (fr.pantheonsorbonne.ufr27.miage.model.Availability currAv : availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate)) {
                Hotel hotel = new Hotel(currAv.getHotel().getHotelName());
                //currentAvailableHotels.add(new fr.pantheonsorbonne.ufr27.miage.dto.Availability(currAv.getBedsNumber(), currAv.getNumberFreeRooms(), hotel, currAv.getDate()));
                currentAvailableHotels.add(hotel);
            }
            availableHotels.retainAll(currentAvailableHotels);

            // Move to the next day
            currentDate.setTime(currentDate.getTime() + 24 * 60 * 60 * 1000); // Add one day
        }

        return availableHotels;
    }

}

