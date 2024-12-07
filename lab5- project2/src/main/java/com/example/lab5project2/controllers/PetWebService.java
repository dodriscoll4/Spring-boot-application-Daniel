package com.example.lab5project2.controllers;


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
@RequestMapping("/myapi/pets")
public class PetWebService {

    private PetService petService;
    private HouseholdService householdService;

    @GetMapping({"/", ""})
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    @GetMapping({"/{id}"})
    public Pet getPetById(@PathVariable("id") int id) throws NotFoundException {
        return petService.getPetById(id);
    }

    @DeleteMapping({"/{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePetById(@PathVariable("id") int id) throws NotFoundException {
        petService.deletePetById(id);
    }

    @PostMapping({"/"})
    @ResponseStatus(HttpStatus.CREATED)
    public Pet createPet(@RequestBody @Valid NewPet newPet)
            throws BadDataException, NotFoundException {
        Household household = null;
        if (newPet.householdEircode() != null && !newPet.householdEircode().isEmpty()) {
            household = householdService.getHouseholdByEircode(newPet.householdEircode());
        }
        Pet pet = new Pet(newPet.name(), newPet.animalType(), newPet.breed(),
                newPet.age(), household);
        return petService.createPet(pet);
    }

    @PatchMapping({"/{id}/{newPetName}"})
    public void updatePetName(@PathVariable int id, @PathVariable String newPetName) throws NotFoundException {
        petService.updatePet(id, newPetName, null, null, 0);
    }

}
