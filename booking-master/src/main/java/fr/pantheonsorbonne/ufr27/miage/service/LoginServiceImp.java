package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.UserDAO;
import fr.pantheonsorbonne.ufr27.miage.exception.UserNotFoundException;
import fr.pantheonsorbonne.ufr27.miage.model.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.CamelContext;

import javax.security.auth.login.AccountNotFoundException;

@ApplicationScoped
public class LoginServiceImp implements LoginService {
    @Inject
    UserDAO userDAO;

    @Override
    public User getAccountByEmailAndPassword(String email, String password) throws UserNotFoundException {
        for (User user : userDAO.getAllUser()) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new UserNotFoundException("User not found for email: " + email + " \nPlease check email and password.");
    }
}
