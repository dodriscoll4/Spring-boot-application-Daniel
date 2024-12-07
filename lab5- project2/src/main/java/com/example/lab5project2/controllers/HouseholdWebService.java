package com.example.lab5project2.controllers;


import com.example.lab5project2.dtos.NewHousehold;
import com.example.lab5project2.dtos.NewPet;
import com.example.lab5project2.entities.Household;
import com.example.lab5project2.entities.Pet;
import com.example.lab5project2.services.HouseholdService;
import com.example.lab5project2.services.PetService;
import com.example.lab5project2.services.exceptions.BadDataException;
import com.example.lab5project2.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/myapi/households")
public class HouseholdWebService {

    private PetService petService;
    private HouseholdService householdService;

    @GetMapping({"/", ""})
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @GetMapping({"/noPets", ""})
    public List<Household> getAllHouseholdsWithoutPets() {
        return householdService.getHouseholdsWithNoPets();
    }

    @GetMapping({"/{eircode}"})
    public Household getHouseholdByEircode(@PathVariable("eircode") String ericode) throws NotFoundException {
        return householdService.getHouseholdByEircode(ericode);
    }

    @DeleteMapping({"/{eircode}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHouseholdByEircode(@PathVariable("eircode") String eircode) throws NotFoundException {
        householdService.deleteHouseholdByEircode(eircode);
    }

    @PostMapping({"/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Household createHousehold(@RequestBody @Valid NewHousehold newHousehold)
            throws BadDataException, NotFoundException {

        Household household = new Household(newHousehold.eircode(), newHousehold.numberOfOccupants(), newHousehold.maxNumberOfOccupants(), newHousehold.ownerOccupied());
        return householdService.createHousehold(household);
    }


}
