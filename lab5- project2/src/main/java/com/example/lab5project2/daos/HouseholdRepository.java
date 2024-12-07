package com.example.lab5project2.daos;

import com.example.lab5project2.entities.Household;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HouseholdRepository extends JpaRepository<Household, String> {
    @EntityGraph(attributePaths = {"pets"})
    Optional<Household> findByEircode(String eircode);

    @Query("SELECT h FROM Household h WHERE h.pets IS EMPTY")
    List<Household> getHouseholdsWithNoPets();

    int deleteByEircode(String eircode);
}
