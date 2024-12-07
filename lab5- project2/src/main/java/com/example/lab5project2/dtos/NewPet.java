package com.example.lab5project2.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewPet(@NotEmpty @NotNull
                     String name,
                     @NotEmpty @NotNull
                     String animalType,
                     @NotEmpty @NotNull
                     String breed,
                     @Min(value = 1, message = "Min age greater than or equal to 1 year")
                     @Max(value = 13, message = "Year must be less than average dog lifespan of 13 years")
                     int age,
                     String householdEircode) {
}
