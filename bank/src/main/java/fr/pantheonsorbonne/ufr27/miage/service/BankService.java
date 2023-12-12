package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;
import fr.pantheonsorbonne.ufr27.miage.dto.TransactionDTO;
import fr.pantheonsorbonne.ufr27.miage.exception.AccountNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.Account;
import fr.pantheonsorbonne.ufr27.miage.model.Bank;
import fr.pantheonsorbonne.ufr27.miage.model.BankTransfer;

import java.util.Collection;


public interface BankService {
    Account getAccountByEmailAndPassword(String email, String password) throws AccountNotFoundException;

    BankTransfer addTransaction(TransactionDTO transaction);

    Account changeAccountBalance(Account account);

}
