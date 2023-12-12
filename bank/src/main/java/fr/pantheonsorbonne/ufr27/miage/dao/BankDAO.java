package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.BankTransfer;

import java.util.Collection;

public interface BankDAO {
    Collection<Account> getAllAccounts();

    BankTransfer save(TransactionDTO transaction);

    Account changeAccountBalance(Account account);
}
