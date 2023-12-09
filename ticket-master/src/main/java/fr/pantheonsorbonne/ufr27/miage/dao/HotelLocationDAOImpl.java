package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.HotelLocation;
import fr.pantheonsorbonne.ufr27.miage.model.Venue;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Collection;

@ApplicationScoped
public class HotelLocationDAOImpl implements HotelLocationDAO{

    @Inject
    EntityManager em;
    @Override
    public Collection<HotelLocation> getHotelLocations() {
        return em.createNamedQuery("getAllHotelLocations", HotelLocation.class).getResultList();
    }
}
