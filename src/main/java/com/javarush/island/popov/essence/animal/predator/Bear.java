package com.javarush.island.popov.essence.animal.predator;

import com.javarush.island.popov.essence.animal.abstraction.Predator;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Bear extends Predator {

    public Bear() {
        super(500, 2, 80, 5, "Bear");
    }


    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Duck" -> 0.1;
            case "Buffalo" -> 0.2;
            case "Horse" -> 0.4;
            case "Boar" -> 0.5;
            case "Goat", "Sheep" -> 0.7;
            case "Deer", "Rabbit", "Python" -> 0.8;
            case "Mouse" -> 0.9;
            default -> 0;
        };
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Bear){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Bear(), cell.getRow(), cell.getCol());
        }
    }
}
