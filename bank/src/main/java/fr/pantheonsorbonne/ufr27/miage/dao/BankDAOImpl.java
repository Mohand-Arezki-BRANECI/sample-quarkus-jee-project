package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Account;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class BankDAOImpl implements BankDAO{

    @Inject
    EntityManager em;

    @Override
    public Collection<Account> getAllAccounts() {
        return em.createNamedQuery("getAllAccounts", Account.class).getResultList();
    }
}
