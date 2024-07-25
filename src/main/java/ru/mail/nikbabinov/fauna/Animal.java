package ru.mail.nikbabinov.fauna;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = Buffalo.class),
        @JsonSubTypes.Type(value = Deer.class),
        @JsonSubTypes.Type(value = Horse.class),
        @JsonSubTypes.Type(value = Rabbit.class),
        @JsonSubTypes.Type(value = Goat.class),
        @JsonSubTypes.Type(value = Sheep.class),
        @JsonSubTypes.Type(value = WildBoar.class),
        @JsonSubTypes.Type(value = Duck.class),
        @JsonSubTypes.Type(value = Mouse.class),
        @JsonSubTypes.Type(value = Caterpillar.class),
        @JsonSubTypes.Type(value = Fox.class),
        @JsonSubTypes.Type(value = BoaConstrictor.class),
        @JsonSubTypes.Type(value = Bear.class),
        @JsonSubTypes.Type(value = Wolf.class),
        @JsonSubTypes.Type(value = Eagle.class),})
public abstract class Animal {
    private int age;
    private double weight;
    private int speed;
    private double weightFoodRemoveHunger;
    private int maxNumbOfSpeciesInOneCell;

    public Animal(int age, double weight, int speed, double weightFoodRemoveHunger, int maxNumbOfSpeciesInOneCell) {
        this.age = age;
        this.weight = weight;
        this.speed = speed;
        this.weightFoodRemoveHunger = weightFoodRemoveHunger;
        this.maxNumbOfSpeciesInOneCell = maxNumbOfSpeciesInOneCell;
    }

    public int getAge() {
        return age;
    }

    public double getWeight() {
        return weight;
    }

    public int getSpeed() {
        return speed;
    }

    public double getWeightFoodRemoveHunger() {
        return weightFoodRemoveHunger;
    }

    public int getMaxNumbOfSpeciesInOneCell() {
        return maxNumbOfSpeciesInOneCell;
    }


    public void setAge(int age) {
        this.age = age;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setWeightFoodRemoveHunger(double weightFoodRemoveHunger) {
        this.weightFoodRemoveHunger = weightFoodRemoveHunger;
    }

    public void setMaxNumbOfSpeciesInOneCell(int maxNumbOfSpeciesInOneCell) {
        this.maxNumbOfSpeciesInOneCell = maxNumbOfSpeciesInOneCell;
    }
}
