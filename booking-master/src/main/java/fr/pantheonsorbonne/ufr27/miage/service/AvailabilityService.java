package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import fr.pantheonsorbonne.ufr27.miage.model.Availability;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface AvailabilityService {


    List<Hotel> getConsistentlyAvailableHotels(int numberOfGuests, Date date, Date endDate);
}
