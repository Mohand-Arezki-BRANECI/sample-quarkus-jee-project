package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.HotelLocation;

import java.util.Collection;

public interface HotelLocationDAO {
    Collection<HotelLocation> getHotelLocations();
}
