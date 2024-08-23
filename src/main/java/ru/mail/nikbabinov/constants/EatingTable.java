package ru.mail.nikbabinov.constants;

import ru.mail.nikbabinov.entity.wildLife.WildLife;
import ru.mail.nikbabinov.entity.wildLife.fauna.Animal;
import ru.mail.nikbabinov.entity.wildLife.flora.Plant;

public class EatingTable {


    private static final int[][] eatTable = {
            {0, 0, 0, 0, 0, 10, 15, 60, 80, 60, 70, 15, 10, 40, 0, 0},
            {0, 0, 15, 0, 0, 0, 0, 20, 40, 0, 0, 0, 0, 10, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 70, 90, 0, 0, 0, 0, 60, 40, 0},
            {0, 80, 0, 0, 0, 40, 80, 80, 90, 70, 70, 50, 20, 10, 0, 0},
            {0, 0, 10, 0, 0, 0, 0, 90, 90, 0, 0, 0, 0, 80, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 10},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 50, 0, 0, 0, 0, 0, 90, 10},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 90, 100},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 100},
    };

    public static int percentEating(WildLife firstWildLifeObject, WildLife secondWildLifeObject) {
        int row = getNumbRatioName(firstWildLifeObject);
        int column = getNumbRatioName(secondWildLifeObject);
        if (firstWildLifeObject instanceof Plant) {
            if (column < 15) {
                return eatTable[column][row];
            }
            return 0;
        }
        if (row < 15) {
            return eatTable[row][column];
        }
        return 0;
    }

    private static int getNumbRatioName(WildLife animalOrPlant) {
        String typeObjetWildLif = getNameClass(animalOrPlant);
        return switch (typeObjetWildLif) {
            case ("BoaConstrictor") -> 1;
            case ("Fox") -> 2;
            case ("Bear") -> 3;
            case ("Eagle") -> 4;
            case ("Horse") -> 5;
            case ("Deer") -> 6;
            case ("Rabbit") -> 7;
            case ("Mouse") -> 8;
            case ("Goat") -> 9;
            case ("Sheep") -> 10;
            case ("WildBoar") -> 11;
            case ("Buffalo") -> 12;
            case ("Duck") -> 13;
            case ("Caterpillar") -> 14;
            case ("Grass") -> 15;
            default -> 0;
        };
    }

    private static String getNameClass(WildLife animalOrPlant) {
        String nameClass = null;
        if (animalOrPlant instanceof Animal) {
            nameClass = ((Animal) animalOrPlant).getClass().getSimpleName();
        }
        if (animalOrPlant instanceof Plant) {
            nameClass = ((Plant) animalOrPlant).getClass().getSimpleName();
        }
        return nameClass;
    }
}
