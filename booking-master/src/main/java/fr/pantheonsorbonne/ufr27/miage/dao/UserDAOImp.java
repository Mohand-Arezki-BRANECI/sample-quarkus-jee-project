package fr.pantheonsorbonne.ufr27.miage.dao;


import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;

import java.util.Collection;

@ApplicationScoped
public class UserDAOImp implements UserDAO {

    @Inject
    EntityManager em;

    @Override
    public Collection<User> getAllUser() {
        return em.createNamedQuery("getAllUser", User.class).getResultList();
    }

    @Override
    public User getUser(String email) {
        try {
            // Assuming 'User' is the name of your entity class
            return em.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (NoResultException e) {
            // Handle the case when no user with the given email is found
            return null;
        }
    }

}