package com.hotel.dao;

import com.hotel.model.User;
import fr.pantheonsorbonne.ufr27.miage.dto.UserDTO;

public interface UserDAO {
    User doesUserExist(UserDTO userDTO);
    User createUser(UserDTO userDTO);
}
