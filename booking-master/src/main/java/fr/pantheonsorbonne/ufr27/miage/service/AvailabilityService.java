package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.Availability;

import java.util.Date;
import java.util.List;

public interface AvailabilityService {

    List<Availability> getConsistentlyAvailableHotels(int numberOfGuests, Date date, Date endDate, int locationId);
}
