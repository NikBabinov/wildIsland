package ru.mail.nikbabinov.fauna;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Fox extends Animal implements PredatoryAnimal {

    @JsonCreator()
    public Fox(@JsonProperty("name") String name,
               @JsonProperty("age") int age,
               @JsonProperty("weight") double weight,
               @JsonProperty("speed") int speed,
               @JsonProperty("weightFoodRemoveHunger") double weightFoodRemoveHunger,
               @JsonProperty("maxNumbOfSpeciesInOneCell") int maxNumbOfSpeciesInOneCell) {
        super(name, age, weight, speed, weightFoodRemoveHunger, maxNumbOfSpeciesInOneCell);
    }
}
