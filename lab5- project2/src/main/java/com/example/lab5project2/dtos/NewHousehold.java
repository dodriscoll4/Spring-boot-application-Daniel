package com.example.lab5project2.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record NewHousehold(
        @NotEmpty @NotNull
        String eircode,
        @Min(value = 1, message = "Number of occupants must be at least 1")
        int numberOfOccupants,
        @Min(value = 1, message = "Max occupants must be at least 1")
        int maxNumberOfOccupants,
        boolean ownerOccupied)
{
}
