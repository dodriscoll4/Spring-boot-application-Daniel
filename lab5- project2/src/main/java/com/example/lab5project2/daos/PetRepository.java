package com.example.lab5project2.daos;

import com.example.lab5project2.dtos.Name;
import com.example.lab5project2.entities.Pet;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface PetRepository extends JpaRepository<Pet, Integer> {

    int deletePetById(int id);

    @Modifying
    @Transactional
    int deletePetsByName(String name);

    List<Pet> findPetsByAnimalType(String animalType);
    List<Pet> findPetsByBreedOrderByAge(String breed);

    @Query("SELECT new com.example.lab5project2.dtos.Name(p.name, p.animalType, p.breed ) FROM Pet p")
    List<Name> findAllNamesAndBreeds();

    @Query("SELECT AVG(p.age) FROM Pet p")
    Optional<Double> findAverageAge();

    @Query("SELECT MAX(p.age) FROM Pet p")
    Optional<Integer> findOldestAge();
}
