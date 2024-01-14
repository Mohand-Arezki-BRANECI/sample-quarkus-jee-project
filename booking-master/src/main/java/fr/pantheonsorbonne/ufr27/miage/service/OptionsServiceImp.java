package fr.pantheonsorbonne.ufr27.miage.service;

import fr.pantheonsorbonne.ufr27.miage.dao.HotelLocationDAO;
import fr.pantheonsorbonne.ufr27.miage.dao.HotelOptionsDAO;
import fr.pantheonsorbonne.ufr27.miage.dto.Hotel;
import fr.pantheonsorbonne.ufr27.miage.dto.HotelOption;
import fr.pantheonsorbonne.ufr27.miage.model.Availability;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequestScoped
public class OptionsServiceImp implements OptionsService{

    @Inject
    HotelOptionsDAO hotelOptionsDAO;
    @Override
   public List<HotelOption> getHotelOptions(int hotelId){

        List<HotelOption> hotelOptions = new ArrayList<>();
        for (fr.pantheonsorbonne.ufr27.miage.model.HotelOption hotelOption : hotelOptionsDAO.getHotelOptions()){
            if(hotelOption.getHotel().getId() == hotelId){
                HotelOption newHotelOption = new HotelOption(hotelOption.getHotel().getId(), hotelOption.getOptionName(), hotelOption.getOptionPrice());
                hotelOptions.add(newHotelOption);
            }
        }
        return hotelOptions;
    }
}
