package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.User;
import java.util.Collection;

public interface UserDAO {
    Collection<User> getAllUser();

    User getUser(String email);

}