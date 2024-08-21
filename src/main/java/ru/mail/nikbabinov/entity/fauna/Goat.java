package ru.mail.nikbabinov.entity.fauna;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Goat extends Animal implements HerbivorousAnimal {

    @JsonCreator()
    public Goat(@JsonProperty("name") String name,
                @JsonProperty("age") int age,
                @JsonProperty("weight") double weight,
                @JsonProperty("speed") int speed,
                @JsonProperty("weightFoodRemoveHunger") double weightFoodRemoveHunger,
                @JsonProperty("startNumbOfSpeciesInOneCell") int startNumbOfSpeciesInOneCell,
                @JsonProperty("maxNumbOfSpeciesInOneCell") int maxNumbOfSpeciesInOneCell) {
        super(name, age, weight, speed, weightFoodRemoveHunger, startNumbOfSpeciesInOneCell, maxNumbOfSpeciesInOneCell);
    }
}
