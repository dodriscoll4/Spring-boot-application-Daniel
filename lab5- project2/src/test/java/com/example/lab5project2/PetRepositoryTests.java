package com.example.lab5project2;

import com.example.lab5project2.daos.PetRepository;
import com.example.lab5project2.dtos.Name;
import com.example.lab5project2.entities.Pet;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
public class PetRepositoryTests {

    @Autowired
    private PetRepository petRepository;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(petRepository);
    }

    @Nested
    @Order(1)
    class TestDelete {

        @Test
        void deletePetById_shouldDeletePet() {
            petRepository.deletePetById(1);
            Optional<Pet> pet = petRepository.findById(1);
            Assertions.assertTrue(pet.isEmpty());
        }

        @Test
        void deletePetsByName_shouldDeletePets() {
            Pet pet = new Pet();
            pet.setId(2); // Assuming the ID for this pet is 2
            pet.setName("Buddy");
            pet.setAnimalType("Dog");
            pet.setBreed("Golden Retriever");
            pet.setAge(4);
            petRepository.save(pet);

            // Attempt to delete the pet by name
            petRepository.deletePetsByName("Buddy");
            Assertions.assertEquals(8, petRepository.count());
        }
    }

    @Nested
    @Order(2)
    class TestFind {

        @Test
        void findPetsByAnimalType_shouldReturnPets() {
            // Assume at least one pet of type "Dog" exists; adjust based on your test data
            List<Pet> dogs = petRepository.findPetsByAnimalType("Dog");
            Assertions.assertFalse(dogs.isEmpty()); // Ensure there are dogs in the list
            Assertions.assertEquals("Dog", dogs.get(0).getAnimalType());
        }

        @Test
        void findPetsByBreedOrderByAge_shouldReturnPets() {
            // Assume at least one pet of breed "Labrador" exists; adjust based on your test data
            List<Pet> beagles = petRepository.findPetsByBreedOrderByAge("Beagle");
            Assertions.assertFalse(beagles.isEmpty()); // Ensure there are Labradors in the list
            Assertions.assertEquals("Beagle", beagles.get(0).getBreed());
        }

        @Test
        void findAllNamesAndBreeds_shouldReturnNamesAndBreeds() {
            List<Name> namesAndBreeds = petRepository.findAllNamesAndBreeds();
            Assertions.assertFalse(namesAndBreeds.isEmpty()); // Ensure the list is not empty
            Assertions.assertNotNull(namesAndBreeds.get(0)); // Ensure the first entry is not null
        }

        @Test
        void findAverageAge_shouldReturnAverageAge() {
            Optional<Double> averageAge = petRepository.findAverageAge();
            Assertions.assertTrue(averageAge.isPresent());
            Assertions.assertTrue(averageAge.get() >= 0); // Ensure the average age is non-negative
        }

        @Test
        void findOldestAge_shouldReturnOldestAge() {
            Optional<Integer> oldestAge = petRepository.findOldestAge();
            Assertions.assertTrue(oldestAge.isPresent());
            Assertions.assertTrue(oldestAge.get() >= 0); // Ensure the oldest age is non-negative
        }
    }}
