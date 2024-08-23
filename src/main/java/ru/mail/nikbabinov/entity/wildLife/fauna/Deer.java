package ru.mail.nikbabinov.entity.wildLife.fauna;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Deer extends Animal implements HerbivorousAnimal {

    @JsonCreator()
    public Deer(@JsonProperty("relation") String relation,
                @JsonProperty("name") String name,
                @JsonProperty("age") int age,
                @JsonProperty("weight") double weight,
                @JsonProperty("speed") int speed,
                @JsonProperty("weightFoodRemoveHunger") double weightFoodRemoveHunger,
                @JsonProperty("startNumbOfSpeciesInOneCell") int startNumbOfSpeciesInOneCell,
                @JsonProperty("maxNumbOfSpeciesInOneCell") int maxNumbOfSpeciesInOneCell) {
        super(relation, name, age, weight, speed, weightFoodRemoveHunger, startNumbOfSpeciesInOneCell, maxNumbOfSpeciesInOneCell);
    }
}
