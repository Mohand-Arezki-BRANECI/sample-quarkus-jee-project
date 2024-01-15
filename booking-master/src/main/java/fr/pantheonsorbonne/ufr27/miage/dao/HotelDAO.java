package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Hotel;

import java.util.Collection;

public interface HotelDAO  {
    Collection<Hotel> getHotelsForLocation(int loactionId);

}
