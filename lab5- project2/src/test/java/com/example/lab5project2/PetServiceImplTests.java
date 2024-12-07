package com.example.lab5project2;

import com.example.lab5project2.entities.Pet;
import com.example.lab5project2.services.PetServiceImpl;
import com.example.lab5project2.services.exceptions.BadDataException;
import com.example.lab5project2.services.exceptions.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@Order(2)
public class PetServiceImplTests {

    @Autowired
    private PetServiceImpl petService;

    @Test
    void contextLoads() {
        Assertions.assertNotNull(petService);
    }

    @Nested
    @Order(1)
    class TestCreatePet {

        @Test
        void createPet_shouldSavePet() throws BadDataException {
            Pet pet = new Pet(); // Using no-argument constructor
            pet.setName("Buddy");
            pet.setAnimalType("Dog");
            pet.setBreed("Golden Retriever");
            pet.setAge(5);

            Pet savedPet = petService.createPet(pet);
            Assertions.assertNotNull(savedPet);
            Assertions.assertEquals("Buddy", savedPet.getName());
        }

        @Test
        void createPet_shouldThrowBadDataException_whenNameIsBlank() {
            Pet pet = new Pet(); // Using no-argument constructor
            pet.setName("");
            pet.setAnimalType("Dog");
            pet.setBreed("Golden Retriever");
            pet.setAge(5);
            Assertions.assertThrows(BadDataException.class, () -> petService.createPet(pet));
        }

        @Test
        void createPet_shouldThrowBadDataException_whenAnimalTypeIsBlank() {
            Pet pet = new Pet(); // Using no-argument constructor
            pet.setName("Buddy");
            pet.setAnimalType("");
            pet.setBreed("Golden Retriever");
            pet.setAge(5);
            Assertions.assertThrows(BadDataException.class, () -> petService.createPet(pet));
        }

        @Test
        void createPet_shouldThrowBadDataException_whenAgeIsNegative() {
            Pet pet = new Pet(); // Using no-argument constructor
            pet.setName("Buddy");
            pet.setAnimalType("Dog");
            pet.setBreed("Golden Retriever");
            pet.setAge(-1);
            Assertions.assertThrows(BadDataException.class, () -> petService.createPet(pet));
        }
    }

    @Nested
    @Order(2)
    class TestGetPetById {

        @Test
        void getPetById_shouldReturnPet() throws NotFoundException {
            Pet pet = new Pet(); // Using no-argument constructor
            pet.setName("Buddy");
            pet.setAnimalType("Dog");
            pet.setBreed("Golden Retriever");
            pet.setAge(5);
            try {
                petService.createPet(pet); // Create the pet to ensure it exists
            } catch (BadDataException e) {
                throw new RuntimeException(e);
            }

            Pet foundPet = petService.getPetById(pet.getId()); // Use the correct id
            Assertions.assertNotNull(foundPet);
            Assertions.assertEquals("Buddy", foundPet.getName());
        }

        @Test
        void getPetById_shouldThrowNotFoundException_whenPetDoesNotExist() {
            Assertions.assertThrows(NotFoundException.class, () -> petService.getPetById(111)); // Assuming this id does not exist
        }
    }

    @Nested
    @Order(3)
    class TestUpdatePet {

        @Test
        void updatePet_shouldUpdatePetDetails() throws NotFoundException, BadDataException {
            Pet pet = new Pet(); // Using no-argument constructor
            pet.setName("Buddy");
            pet.setAnimalType("Dog");
            pet.setBreed("Golden Retriever");
            pet.setAge(5);
            pet = petService.createPet(pet); // Create the pet to ensure it exists

            Pet updatedPet = petService.updatePet(pet.getId(), "Max", "Dog", "Bulldog", 3);
            Assertions.assertEquals("Max", updatedPet.getName());
            Assertions.assertEquals("Bulldog", updatedPet.getBreed());
        }

        @Test
        void updatePet_shouldThrowNotFoundException_whenPetIdNotFound() {
            Assertions.assertThrows(NotFoundException.class, () -> petService.updatePet(111, "Max", "Dog", "Bulldog", 3)); // Assuming this id does not exist
        }
    }
}
