package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.BankDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;




@RequestScoped
public class BankServiceImp implements BankService {

    @PersistenceContext
    EntityManager em;

    @Inject
    BankDAO accountDAO;

    @Override
    public Account getAccountByEmailAndPassword(String email, String password) throws AccountNotFoundException {
        for (Account account : accountDAO.getAllAccounts()) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                return account;
            }
        }

        throw new AccountNotFoundException("User not found for email: " + email);

    }
}
