package com.example.lab5project2.controllers;

import com.example.lab5project2.dtos.NewHousehold;
import com.example.lab5project2.entities.Household;
import com.example.lab5project2.entities.Pet;
import com.example.lab5project2.services.HouseholdService;
import com.example.lab5project2.services.PetService;
import com.example.lab5project2.services.exceptions.BadDataException;
import com.example.lab5project2.services.exceptions.NotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
public class GraphQLAPI {
    private PetService petService;
    private HouseholdService householdService;

    @QueryMapping
    List<Household> findAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    List<Pet> findPetsByAnimalType(@Argument String animalType) throws NotFoundException {
        return petService.getPetsByAnimalType(animalType);
    }

    @QueryMapping
    Household findHouseholdByEircode(@Argument String eircode) throws NotFoundException {
        return householdService.getHouseholdByEircode(eircode);
    }

    @QueryMapping
    Pet findPetById(@Argument int petId) throws NotFoundException {
        return petService.getPetById(petId);
    }

    @QueryMapping
    Map<String, Object> getPetStatistics(){
        return petService.getPetStatistics();
    }

    @Secured(value = {"ROLE_ADMIN"})
    @MutationMapping
    Household createHousehold(@Valid @Argument("household") NewHousehold newHousehold) throws BadDataException, NotFoundException {
        Household household = new Household(newHousehold.eircode(), newHousehold.numberOfOccupants(), newHousehold.maxNumberOfOccupants(), newHousehold.ownerOccupied());
        return householdService.createHousehold(household);
    }

    @Secured(value = {"ROLE_ADMIN"})
    @MutationMapping
    int deleteHousehold(@Argument String eircode) throws NotFoundException {
        householdService.deleteHouseholdByEircode(eircode);
        return 1;
    }

    @Secured(value = {"ROLE_ADMIN"})
    @MutationMapping
    int deletePet(@Argument int petId) throws NotFoundException {
        petService.deletePetById(petId);
        return 1;
    }

}

