package ru.mail.nikbabinov.controller;

import javafx.collections.ObservableList;
import ru.mail.nikbabinov.app.MainApplication;
import ru.mail.nikbabinov.constants.EatingTable;
import ru.mail.nikbabinov.entity.wildLife.WildLife;
import ru.mail.nikbabinov.entity.wildLife.fauna.Animal;
import ru.mail.nikbabinov.entity.wildLife.flora.Plant;

import java.util.concurrent.*;

public class AnimalActionController {
    private static final int widthIsland = ConfigApplicationController.getSizeIsland("width");
    private static final int heightIsland = ConfigApplicationController.getSizeIsland("height");
    private static int numbOfRemoveGrassStep = 0;

    public static void animalMove() {

        int randomCol = ThreadLocalRandom.current().nextInt(0, widthIsland);
        int randomRow = ThreadLocalRandom.current().nextInt(0, heightIsland);
        String keyMap = (randomRow + ":" + randomCol);
        int randomCell = 0;
        if (MainApplication.getWildIslandMap().get(keyMap).size() - 1 > 0) {
            randomCell = ThreadLocalRandom.current().nextInt(0, MainApplication.getWildIslandMap().get(keyMap).size() - 1);
        }
        ObservableList<WildLife> wildLife = MainApplication.getWildIslandMap().get(keyMap);
        if (!MainApplication.getWildIslandMap().get(keyMap).isEmpty()) {
            WildLife objectInField = wildLife.get(randomCell);
            int numberOfStep = 0;
            if (objectInField instanceof Animal) {
                numberOfStep = ((Animal) objectInField).getSpeed();
            }
            String randomSelectDirectionMovement = randomSelectDirectionMovement();
            String keyMovement = getKeyMovement(randomSelectDirectionMovement, keyMap, numberOfStep);
            ObservableList<WildLife> lastAnimalList = MainApplication.getWildIslandMap().get(keyMovement);
            synchronized (MainApplication.getWildIslandMap()) {
                if (lastAnimalList != null) {
                    if (objectInField instanceof Animal) {
                        if (checkMaxNumbAnimalInOnCell(objectInField, lastAnimalList)) {
                            if (isLife(objectInField, lastAnimalList)) {
                                lastAnimalList.add(objectInField);
                            }
                            if (wildLife.size() < randomCell) {
                                wildLife.remove(randomCell);
                            }
                        }
                    }
                    if (objectInField instanceof Plant) {
                        if (isLife(objectInField, wildLife)) {
                            wildLife.remove(objectInField);
                            numbOfRemoveGrassStep++;
                        }
                        if (numbOfRemoveGrassStep == 10) {
                            wildLife.add(objectInField);
                            numbOfRemoveGrassStep = 0;
                        }
                    }
                }
            }
        }
    }

    private static boolean checkMaxNumbAnimalInOnCell(WildLife animal, ObservableList<WildLife> lastAnimalList) {
        final int[] currentNumbAnimalInCellMove = {0};
        if (animal instanceof Animal) {
            for (WildLife animalInCell : lastAnimalList) {
                if (animalInCell instanceof Animal) {
                    if (((Animal) animalInCell).getName().equals(((Animal) animal).getName())) {
                        currentNumbAnimalInCellMove[0]++;
                    }
                }
            }
        }
        if (animal instanceof Animal) {
            return currentNumbAnimalInCellMove[0] < ((Animal) animal).getMaxNumbOfSpeciesInOneCell();
        }
        return currentNumbAnimalInCellMove[0] < ((Plant) animal).getMaxNumbOfSpeciesInOneCell();
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

    private static boolean isLife(WildLife objectInField, ObservableList<WildLife> lastAnimalList) {
        for (WildLife animalInCell : lastAnimalList) {
            if (randomLife(objectInField, animalInCell)) {
                return true;
            }
        }
        return false;
    }

    private static boolean randomLife(WildLife objectInField1, WildLife objectInField2) {
        int percentEating = 10 - (EatingTable.percentEating(objectInField1, objectInField2)) / 10;
        int randomLifePercent = 0;
        if (percentEating > 0) {
            randomLifePercent = ThreadLocalRandom.current().nextInt(0, percentEating);
        }
        return percentEating / 2 > randomLifePercent;
    }
}
