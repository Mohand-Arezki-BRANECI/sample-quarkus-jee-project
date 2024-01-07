package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
@ApplicationScoped
public class AvailabilityDAOImpl implements AvailabilityDAO{
    @Inject
    private EntityManager entityManager;
   /* @Override
    public List<Hotel> getConsistentlyAvailableHotels(int numberOfGuests, Date startDate, Date endDate) {
        List<Hotel> availableHotels = new ArrayList<>();

        // Get all hotels available for the first day
        List<Hotel> initialAvailableHotels = getAvailableHotelsForDate(numberOfGuests, startDate);
        availableHotels.addAll(initialAvailableHotels);

        // Iterate over the date range
        Date currentDate = new Date(startDate.getTime());
        while (!currentDate.after(endDate)) {
            // Get hotels available for the current day
            List<Hotel> currentAvailableHotels = getAvailableHotelsForDate(numberOfGuests, currentDate);

            // Retain only hotels that are consistently available
            availableHotels.retainAll(currentAvailableHotels);

            // Move to the next day
            currentDate.setTime(currentDate.getTime() + 24 * 60 * 60 * 1000); // Add one day
        }

        return availableHotels;
    }*/
    @Override
    public Collection<Availability> getAvailableHotelsForDate(int numberOfGuests, Date date) {

        TypedQuery<Availability> query = entityManager.createNamedQuery("findByDateAndGuests", Availability.class);
        query.setParameter("date", date);
        query.setParameter("numberOfGuests", numberOfGuests);

        return query.getResultList();
    }


}
