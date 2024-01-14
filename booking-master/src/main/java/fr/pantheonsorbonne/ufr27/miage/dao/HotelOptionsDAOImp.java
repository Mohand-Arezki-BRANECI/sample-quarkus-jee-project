package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import fr.pantheonsorbonne.ufr27.miage.model.HotelOption;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

@ApplicationScoped
public class HotelOptionsDAOImp implements HotelOptionsDAO{

    @Inject
    private EntityManager entityManager;

    @Override
    public List<HotelOption> getHotelOptions(){
        TypedQuery<HotelOption> query = entityManager.createNamedQuery("findHotelOptions", HotelOption.class);

        return query.getResultList();
    }
}
