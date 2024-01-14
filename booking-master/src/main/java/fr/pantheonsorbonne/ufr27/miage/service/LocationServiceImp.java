package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.HotelLocationDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.Collection;
import java.util.LinkedList;

@RequestScoped
public class LocationServiceImp implements LocationService {

    @Inject
    HotelLocationDAO hotelLocationDAO;

    @Override
    public Collection<HotelLocation> getHotelLocations() {
        Collection<HotelLocation> hotelLocations = new LinkedList<>();
        for (fr.pantheonsorbonne.ufr27.miage.model.HotelLocation hotelLocation : hotelLocationDAO.getHotelLocations()) {
            hotelLocations.add(new HotelLocation(hotelLocation.getLocationName(),hotelLocation.getLongitude(), hotelLocation.getLongitude(),  hotelLocation.getId()));
        }
        return hotelLocations;
    }
}
