package ru.mail.nikbabinov.controller;

import javafx.collections.ObservableList;
import ru.mail.nikbabinov.entity.fauna.Animal;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

public class AnimalActionController {
    private static final int widthIsland = ConfigApplicationController.getSizeIsland("width");
    private static final int heightIsland = ConfigApplicationController.getSizeIsland("height");

    public static void animalMove(Animal animal, String key, ConcurrentHashMap<String, ObservableList<Animal>> wildIslandMap) {
        ObservableList<Animal> animalsInLine = wildIslandMap.get(key);
        for (Animal animalFirst : animalsInLine) {
            if (animalFirst.getName().equalsIgnoreCase(animal.getName())) {
                int numberOfStep = animal.getSpeed();
                String randomSelectDirectionMovement = randomSelectDirectionMovement();
                String keyMovement = getKeyMovement(randomSelectDirectionMovement, key, numberOfStep);
                if (!keyMovement.equalsIgnoreCase(key)) synchronized (wildIslandMap) {
                    ObservableList<Animal> lastAnimalList = wildIslandMap.get(keyMovement);
                    if(checkMaxNumbAnimalInOnCell(animal,lastAnimalList)){
                        lastAnimalList.add(animal);
                        ObservableList<Animal> firstAnimalList = wildIslandMap.get(key);
                        firstAnimalList.remove(animal);
                    }
                }
            }
        }
    }


    private static String getKeyMovement(String randomSelectDirectionMovement, String key, int numberOfStep) {
        String[] keys = key.split(":");
        int row = Integer.parseInt(keys[0]);
        int col = Integer.parseInt(keys[1]);
        return switch (randomSelectDirectionMovement) {
            case "right" -> {
                if ((numberOfStep + row) > widthIsland) {
                    yield key;
                }
                yield ((row + numberOfStep) + ":" + col);
            }
            case "left" -> {
                if ((row - numberOfStep) < 0) {
                    yield key;
                }
                yield ((row - numberOfStep) + ":" + col);
            }
            case "up" -> {
                if ((col - numberOfStep) < 0) {
                    yield key;
                }
                yield (row + ":" + (col - numberOfStep));
            }
            case "down" -> {
                if ((col + numberOfStep) > heightIsland) {
                    yield key;
                }
                yield ((col + numberOfStep) + ":" + row);
            }
            default -> key;

        };
    }

    private static String randomSelectDirectionMovement() {
        int randomNumberAnimalInOneCell = ThreadLocalRandom.current().nextInt(0, 3);
        return switch (randomNumberAnimalInOneCell) {
            case 0 -> "left";
            case 1 -> "right";
            case 2 -> "up";
            default -> "down";
        };
    }

    private static boolean checkMaxNumbAnimalInOnCell(Animal animal,ObservableList<Animal> lastAnimalList) {
        int currentNumbAnimalInCellMove = 0;
        for (Animal animalInCell : lastAnimalList) {
            if (animalInCell.getName().equals(animal.getName())) {
                currentNumbAnimalInCellMove++;
            }
        }
        return currentNumbAnimalInCellMove < animal.getMaxNumbOfSpeciesInOneCell();
    }
}
