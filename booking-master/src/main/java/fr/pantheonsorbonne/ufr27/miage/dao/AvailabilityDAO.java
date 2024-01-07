package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;

import java.util.Collection;
import java.util.Date;
import java.util.List;

public interface AvailabilityDAO {

    //List<Hotel> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate);
    Collection<Availability> getAvailableHotelsForDate(int numberOfGuests, Date date);

}
