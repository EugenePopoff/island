package com.javarush.island.popov.essence.animal.predator;

import com.javarush.island.popov.essence.animal.abstraction.Predator;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Python extends Predator {
    public Python() {
        super(15, 1, 3, 30, "Python");
    }


    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Duck" -> 0.1;
            case "Fox" -> 0.15;
            case "Rabbit" -> 0.2;
            case "Mouse" -> 0.4;
            default -> 0;
        };
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Python) {
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Python(), cell.getRow(), cell.getCol());
        }
    }
}
