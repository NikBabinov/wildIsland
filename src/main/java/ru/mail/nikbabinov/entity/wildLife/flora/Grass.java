package ru.mail.nikbabinov.entity.wildLife.flora;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Grass extends Plant {

    @JsonCreator()
    public Grass(@JsonProperty("relation") String relation,
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
