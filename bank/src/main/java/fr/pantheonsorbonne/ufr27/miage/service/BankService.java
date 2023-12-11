package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;
import fr.pantheonsorbonne.ufr27.miage.model.Account;

import java.util.Collection;


public interface BankService {
    Account getAccountByEmailAndPassword(String email, String password);

}
