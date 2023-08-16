package com.javarush.island.popov.essence.animal.predator;

import com.javarush.island.popov.essence.animal.abstraction.Predator;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Wolf extends Predator {
    public Wolf() {
        super(50, 3, 8, 30, "Wolf");
    }


    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Horse", "Buffalo" -> 0.1;
            case "Deer", "Boar" -> 0.15;
            case "Duck" -> 0.4;
            case "Goat", "Rabbit" -> 0.6;
            case "Sheep" -> 0.7;
            case "Mouse" -> 0.8;
            default -> 0;
        };
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Wolf) {
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Wolf(), cell.getRow(), cell.getCol());
        }
    }
}
