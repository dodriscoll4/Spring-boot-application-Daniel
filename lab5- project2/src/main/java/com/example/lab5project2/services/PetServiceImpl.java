package com.example.lab5project2.services;


import com.example.lab5project2.dtos.Name;
import com.example.lab5project2.entities.Pet;
import com.example.lab5project2.daos.PetRepository;
import com.example.lab5project2.services.exceptions.BadDataException;
import com.example.lab5project2.services.exceptions.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Data
@Service
@AllArgsConstructor

public class PetServiceImpl implements PetService {
    private PetRepository petRepository;

    @Override
    public Pet createPet(Pet pet) throws BadDataException {
        if (pet.getName().isBlank() || pet.getAnimalType().isBlank()){
            throw new BadDataException("Pet name and type cannot be empty");
        }
        if (pet.getAge() < 0) {
            throw new BadDataException("Age cannot be negative");
        }
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {return petRepository.findAll();}

    @Override
    public Pet getPetById(int id) throws NotFoundException {
        return petRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Pet not found")
        );
    }

    @Override
    public Pet updatePet(int id, String newName, String newAnimalType, String newBreed, int newAge) throws NotFoundException {
        Pet updatedPet = petRepository.findById(id).orElseThrow(()
                    -> new NotFoundException("Pet not found")
        );
        updatedPet.setName(newName != null ? newName : updatedPet.getName());
        updatedPet.setAnimalType(newAnimalType != null ? newAnimalType : updatedPet.getAnimalType());
        updatedPet.setBreed(newBreed != null ? newBreed : updatedPet.getBreed());
        updatedPet.setAge(newAge != 0 ? newAge : updatedPet.getAge());

        petRepository.save(updatedPet);
        return updatedPet;

    }

    @Override
    @Transactional
    public void deletePetById(int id) throws NotFoundException {
        int rowsAffected = petRepository.deletePetById(id);
        if (rowsAffected != 1) {
            throw new NotFoundException("Pet ID Not found:" + id);
        }
    }

    @Override
    public void deletePetsByName(String petName) throws NotFoundException {
        if (petRepository.deletePetsByName(petName.toUpperCase()) == 0) {
            throw new NotFoundException("Pet Name Not found:" + petName);
        }
    }

    @Override
    public List<Pet> getPetsByAnimalType(String animalType) throws NotFoundException {
        List<Pet> pets = petRepository.findPetsByAnimalType(animalType);
        if (pets.isEmpty()) {
            throw new NotFoundException("No pets found for animal type: " + animalType);
        }
        return pets;
    }

    @Override
    public List<Pet> getPetsByBreed(String breed) throws NotFoundException{
        List<Pet> pets = petRepository.findPetsByBreedOrderByAge(breed);
        if (pets.isEmpty()) {
            throw new NotFoundException("No pets found for breed: " + breed);
        }
        return pets;
    }

    @Override
    public List<Name> getNameAndBreedRecords() throws NotFoundException{
        List<Name> namesAndBreeds = petRepository.findAllNamesAndBreeds();
        if (namesAndBreeds.isEmpty()) {
            throw new NotFoundException("No name and breed records found.");
        }
        return namesAndBreeds;
    }

    @Override
    public Map<String, Object> getPetStatistics() {
        Optional<Double> averageAge = petRepository.findAverageAge();
        Optional<Integer> oldestAge = petRepository.findOldestAge();
        long totalPets = petRepository.count();

        Map<String, Object> statistics = new HashMap<>();
        statistics.put("averageAge", averageAge.orElse(0.0));
        statistics.put("oldestAge", oldestAge.orElse(0));
        statistics.put("totalCount", totalPets);

        return statistics;

    }
}
