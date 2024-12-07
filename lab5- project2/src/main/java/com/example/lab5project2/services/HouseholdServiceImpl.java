//test these if they work

package com.example.lab5project2.services;

import com.example.lab5project2.daos.HouseholdRepository;
import com.example.lab5project2.entities.Household;
import com.example.lab5project2.entities.Pet;
import com.example.lab5project2.services.exceptions.BadDataException;
import com.example.lab5project2.services.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Data
@Service
@AllArgsConstructor
public class HouseholdServiceImpl implements HouseholdService {
    private final HouseholdRepository householdRepository;

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household getHouseholdByEircode(String eircode) throws NotFoundException {
        return householdRepository.findById(eircode).orElseThrow(
                () -> new NotFoundException("No such household " + eircode)
        );
    }

    @Override
    public Household getHouseholdByEircodeWithPets(String eircode) throws NotFoundException {
        return householdRepository.findByEircode(eircode).orElseThrow(
                () -> new NotFoundException("No such household " + eircode)
        );
    }


    @Override
    public List<Household> getHouseholdsWithNoPets() {
        return householdRepository.getHouseholdsWithNoPets();
    }

    @Override
    public Household createHousehold(Household household) throws BadDataException {
        if (household.getEircode().isEmpty()){
            throw new BadDataException("Eircode is empty");
        }
        if (household.getNumberOfOccupants() < 1 || household.getMaxNumberOfOccupants() < 1) {
            throw new BadDataException("Occupants cannot be less than 1");
        }
        return householdRepository.save(household);
    }

    @Override
    @Transactional
    public void deleteHouseholdByEircode(String eircode) throws NotFoundException {
        int rowsAffected = householdRepository.deleteByEircode(eircode);
        if (rowsAffected != 1) {
            throw new NotFoundException("Eircode Not found:" + eircode);
        }
    }

}
