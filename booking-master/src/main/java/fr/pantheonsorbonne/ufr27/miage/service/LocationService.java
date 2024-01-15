package fr.pantheonsorbonne.ufr27.miage.service;


import fr.pantheonsorbonne.ufr27.miage.dto.HotelLocation;


import java.util.Collection;


public interface LocationService {
    Collection<HotelLocation> getHotelLocations();
}
