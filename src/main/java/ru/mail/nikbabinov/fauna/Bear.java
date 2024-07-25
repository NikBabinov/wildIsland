package ru.mail.nikbabinov.fauna;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Bear extends Animal implements PredatoryAnimal {

    @JsonCreator()
    public Bear(@JsonProperty("age") int age,
                @JsonProperty("weight") double weight,
                @JsonProperty("speed") int speed,
                @JsonProperty("weightFoodRemoveHunger") double weightFoodRemoveHunger,
                @JsonProperty("maxNumbOfSpeciesInOneCell") int maxNumbOfSpeciesInOneCell) {
        super(age, weight, speed, weightFoodRemoveHunger, maxNumbOfSpeciesInOneCell);
    }
}
