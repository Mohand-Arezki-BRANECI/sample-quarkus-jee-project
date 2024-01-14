package fr.pantheonsorbonne.ufr27.miage.dao;


import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Collection;

@ApplicationScoped
public class UserDAOImp implements UserDAO {

    @Inject
    EntityManager em;

    @Override
    public Collection<User> getAllUser() {
        return em.createNamedQuery("getAllUser", User.class).getResultList();
    }
}