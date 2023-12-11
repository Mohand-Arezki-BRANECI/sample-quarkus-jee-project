package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Account;

import java.util.Collection;

public interface BankDAO {
    Collection<Account> getAllAccounts();

}
