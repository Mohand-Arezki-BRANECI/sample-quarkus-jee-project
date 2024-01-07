package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.Availability;
import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;

import java.util.Date;
import java.util.List;

public interface AvailabilityService {


    List<Hotel> getConsistentlyAvailableHotels(int numberOfGuests, Date date, Date endDate);
}
