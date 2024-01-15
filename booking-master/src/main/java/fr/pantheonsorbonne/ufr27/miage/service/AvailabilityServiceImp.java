package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.AvailabilityDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;


import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
public class AvailabilityServiceImp implements AvailabilityService  {

    @Inject
    AvailabilityDAO availabilityDAO;

    @Override
    public List<fr.pantheonsorbonne.ufr27.miage.dto.Availability> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate, int locationId) {
        List<fr.pantheonsorbonne.ufr27.miage.dto.Availability> availabilitys = new ArrayList<>();
        for (Availability initAv : availabilityDAO.getAvailableHotelsForDate(numberOfGuests, startDate)) {
            if (initAv.getHotel().getHotelLocation().getId() == locationId) {
                Hotel hotel = new Hotel(initAv.getHotel().getHotelName(), initAv.getHotel().getId());
                fr.pantheonsorbonne.ufr27.miage.dto.Availability availability = new fr.pantheonsorbonne.ufr27.miage.dto.Availability(hotel, initAv.getRoomPrice());
                availabilitys.add(availability);
            }
        }

        Date currentDate = new Date(startDate.getTime());
        while (!currentDate.after(endDate)) {
            List<fr.pantheonsorbonne.ufr27.miage.dto.Availability> currentAvailabilitys = new ArrayList<>();
            for (fr.pantheonsorbonne.ufr27.miage.model.Availability currAv : availabilityDAO.getAvailableHotelsForDate(numberOfGuests, currentDate)) {
                if (currAv.getHotel().getHotelLocation().getId() == locationId) {
                    Hotel hotel = new Hotel(currAv.getHotel().getHotelName(), currAv.getHotel().getId());
                    fr.pantheonsorbonne.ufr27.miage.dto.Availability availability = new fr.pantheonsorbonne.ufr27.miage.dto.Availability(hotel, currAv.getRoomPrice());
                    currentAvailabilitys.add(availability);
                }
            }

            availabilitys = availabilitys.stream()
                    .filter(availability -> currentAvailabilitys.stream()
                            .anyMatch(currentAvailability -> currentAvailability.getHotel().getHotelName().equals(availability.getHotel().getHotelName())))
                    .collect(Collectors.toList());

            // Move to the next day
            currentDate.setTime(currentDate.getTime() + 24 * 60 * 60 * 1000);
        }

        return availabilitys;
    }
}

