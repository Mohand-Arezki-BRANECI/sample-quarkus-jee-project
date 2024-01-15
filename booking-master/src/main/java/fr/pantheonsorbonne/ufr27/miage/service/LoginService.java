package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.exception.UserNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.User;

public interface LoginService {
    User getAccountByEmailAndPassword(String email, String password) throws UserNotFoundException;

    User getByEmail(String email);
}
