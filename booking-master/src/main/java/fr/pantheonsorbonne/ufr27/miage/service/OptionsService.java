package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dto.HotelOption;

import java.util.Collection;
import java.util.List;

public interface OptionsService {

    List<HotelOption> getHotelOptions(int hotelId);
    List<fr.pantheonsorbonne.ufr27.miage.model.HotelOption> getHotelOptionsModel(int hotelId);

}
