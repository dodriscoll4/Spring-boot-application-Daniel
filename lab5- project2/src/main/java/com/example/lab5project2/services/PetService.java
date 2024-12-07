package com.example.lab5project2.services;

import com.example.lab5project2.dtos.Name;
import com.example.lab5project2.entities.Pet;
import com.example.lab5project2.services.exceptions.BadDataException;
import com.example.lab5project2.services.exceptions.NotFoundException;

import java.util.List;
import java.util.Map;

public interface PetService {
//implement data is bad error
    Pet createPet(Pet pet) throws BadDataException;
    List<Pet> getAllPets();
    Pet getPetById(int id) throws NotFoundException;
    Pet updatePet(int id, String name, String type, String breed, int age) throws NotFoundException; //add checks in service layer
    void deletePetById(int id) throws NotFoundException;
    void deletePetsByName(String name) throws NotFoundException; // ignore case sensitivity
    List<Pet> getPetsByAnimalType(String animalType) throws NotFoundException;
    List<Pet> getPetsByBreed(String breed) throws NotFoundException; //display animal type too
    List<Name> getNameAndBreedRecords() throws NotFoundException;
    Map<String, Object> getPetStatistics(); // average age and total count

}
