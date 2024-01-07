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
    public List<fr.pantheonsorbonne.ufr27.miage.dto.Availability> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate) {
        List<fr.pantheonsorbonne.ufr27.miage.dto.Availability> availableHotels = new ArrayList<>();

        /*List<Availability> initialAvailableHotels = availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate);
        availableHotels.addAll(initialAvailableHotels); */
        for (fr.pantheonsorbonne.ufr27.miage.model.Availability initAv : availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate)) {
            Hotel hotel = new Hotel(initAv.getHotel().getHotelName());
            availableHotels.add(new fr.pantheonsorbonne.ufr27.miage.dto.Availability(initAv.getBedsNumber(), initAv.getNumberFreeRooms(), hotel, initAv.getDate()));
        }
        Date currentDate = new Date(startDate.getTime());
        /*while (!currentDate.after(endDate)) {
            List<fr.pantheonsorbonne.ufr27.miage.dto.Availability> currentAvailableHotels = new ArrayList<>();
            for (fr.pantheonsorbonne.ufr27.miage.model.Availability currAv : availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate)) {
                Hotel hotel = new Hotel(currAv.getHotel().getHotelName());
                currentAvailableHotels.add(new fr.pantheonsorbonne.ufr27.miage.dto.Availability(currAv.getBedsNumber(), currAv.getNumberFreeRooms(), hotel, currAv.getDate()));
            }
            availableHotels.retainAll(currentAvailableHotels);

            // Move to the next day
            currentDate.setTime(currentDate.getTime() + 24 * 60 * 60 * 1000); // Add one day
        }*/

        return availableHotels;
    }

}

