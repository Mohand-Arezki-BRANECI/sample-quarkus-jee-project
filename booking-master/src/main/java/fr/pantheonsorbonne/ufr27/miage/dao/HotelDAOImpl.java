package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import fr.pantheonsorbonne.ufr27.miage.model.Hotel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Collection;
import java.util.Date;
@ApplicationScoped
public class HotelDAOImpl implements HotelDAO {

    @Inject
    private EntityManager entityManager;
    @Override
    public Collection<Hotel> getHotelsForLocation(int locationId){
        TypedQuery<Hotel> query = entityManager.createNamedQuery("findByLocationId", Hotel.class);
        query.setParameter("locationId", locationId);


        return query.getResultList();
    };

}
