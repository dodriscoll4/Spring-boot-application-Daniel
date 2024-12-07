package com.example.lab5project2.services;

import com.example.lab5project2.entities.Household;
import com.example.lab5project2.services.exceptions.BadDataException;
import com.example.lab5project2.services.exceptions.NotFoundException;
import java.util.List;

public interface HouseholdService {
    Household getHouseholdByEircode(String eircode) throws NotFoundException;
    Household getHouseholdByEircodeWithPets(String eircode) throws NotFoundException;
    List<Household> getHouseholdsWithNoPets();
    List<Household> getAllHouseholds();
    Household createHousehold(Household household) throws BadDataException;
    void deleteHouseholdByEircode(String eircode) throws NotFoundException;
}
