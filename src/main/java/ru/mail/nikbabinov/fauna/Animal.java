package ru.mail.nikbabinov.fauna;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import javafx.beans.property.*;


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
    private final StringProperty name;
    private final IntegerProperty age;
    private final DoubleProperty weight;
    private final IntegerProperty speed;
    private final DoubleProperty weightFoodRemoveHunger;
    private final IntegerProperty startNumbOfSpeciesInOneCell;
    private final IntegerProperty maxNumbOfSpeciesInOneCell;

    public Animal(String name, int age, double weight, int speed, double weightFoodRemoveHunger, int startNumbOfSpeciesInOneCell, int maxNumbOfSpeciesInOneCell) {
        this.name = new SimpleStringProperty(name);
        this.age = new SimpleIntegerProperty(age);
        this.weight = new SimpleDoubleProperty(weight);
        this.speed = new SimpleIntegerProperty(speed);
        this.weightFoodRemoveHunger = new SimpleDoubleProperty(weightFoodRemoveHunger);
        this.startNumbOfSpeciesInOneCell = new SimpleIntegerProperty(startNumbOfSpeciesInOneCell);
        this.maxNumbOfSpeciesInOneCell = new SimpleIntegerProperty(maxNumbOfSpeciesInOneCell);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty startNumbOfSpeciesInOneCellProperty() {
        return startNumbOfSpeciesInOneCell;
    }

    public IntegerProperty maxNumbOfSpeciesInOneCellProperty() {
        return maxNumbOfSpeciesInOneCell;
    }

    public DoubleProperty weightProperty() {
        return weight;
    }

    public IntegerProperty speedProperty() {
        return speed;
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public String getName() {
        return name.get();
    }

    public int getAge() {
        return age.get();
    }

    public double getWeight() {
        return weight.get();
    }

    public int getSpeed() {
        return speed.get();
    }

    public double getWeightFoodRemoveHunger() {
        return weightFoodRemoveHunger.get();
    }

    public int getStartNumbOfSpeciesInOneCell() {
        return startNumbOfSpeciesInOneCell.get();
    }

    public int getMaxNumbOfSpeciesInOneCell() {
        return maxNumbOfSpeciesInOneCell.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }


    public void setAge(int age) {
        this.age.set(age);
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    public void setSpeed(int speed) {
        this.speed.set(speed);
    }

    public void setWeightFoodRemoveHunger(double weightFoodRemoveHunger) {
        this.weightFoodRemoveHunger.set(weightFoodRemoveHunger);
    }

    public void setStartNumbOfSpeciesInOneCell(int startNumbOfSpeciesInOneCell) {
        this.startNumbOfSpeciesInOneCell.set(startNumbOfSpeciesInOneCell);
    }

    public void setMaxNumbOfSpeciesInOneCell(int maxNumbOfSpeciesInOneCell) {
        this.maxNumbOfSpeciesInOneCell.set(maxNumbOfSpeciesInOneCell);
    }
}
