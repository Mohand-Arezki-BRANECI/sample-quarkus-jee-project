package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.AvailabilityDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.HotelLocationDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;
import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.*;
@RequestScoped
public class AvailabilityServiceImp implements AvailabilityService  {

    @PersistenceContext
    private EntityManager entityManager;
    @Inject
    AvailabilityDAO availabilityDAO;
    @Override
    public List<fr.pantheonsorbonne.ufr27.miage.dto.Hotel> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate) {
        List<fr.pantheonsorbonne.ufr27.miage.dto.Hotel> availableHotels = new ArrayList<fr.pantheonsorbonne.ufr27.miage.dto.Hotel>();

        /*List<Availability> initialAvailableHotels = availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate);
        availableHotels.addAll(initialAvailableHotels); */
        for (fr.pantheonsorbonne.ufr27.miage.model.Availability initAv : availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate)) {
            Hotel hotel = new Hotel(initAv.getHotel().getHotelName());
            //availableHotels.add(new fr.pantheonsorbonne.ufr27.miage.dto.Availability(initAv.getBedsNumber(), initAv.getNumberFreeRooms(), hotel, initAv.getDate()));
            availableHotels.add(hotel);
        }
        Date currentDate = new Date(startDate.getTime());
        while (!currentDate.after(endDate)) {
            List<fr.pantheonsorbonne.ufr27.miage.dto.Hotel> currentAvailableHotels = new ArrayList<>();
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

