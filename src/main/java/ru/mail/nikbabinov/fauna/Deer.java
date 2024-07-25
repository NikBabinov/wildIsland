package ru.mail.nikbabinov.fauna;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Deer extends Animal implements HerbivorousAnimal {

    @JsonCreator()
    public Deer(@JsonProperty("age") int age,
                @JsonProperty("weight") double weight,
                @JsonProperty("speed") int speed,
                @JsonProperty("weightFoodRemoveHunger") double weightFoodRemoveHunger,
                @JsonProperty("maxNumbOfSpeciesInOneCell") int maxNumbOfSpeciesInOneCell) {
        super(age, weight, speed, weightFoodRemoveHunger, maxNumbOfSpeciesInOneCell);
    }
}
