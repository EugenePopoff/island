package com.javarush.island.popov.essence.animal.predator;

import com.javarush.island.popov.essence.animal.abstraction.Predator;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Eagle extends Predator {

    public Eagle() {
        super(6, 3, 1, 20, "Eagle");
    }


    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Fox" -> 0.1;
            case "Duck" -> 0.8;
            case "Rabbit", "Mouse" -> 0.9;
            default -> 0;
        };
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Eagle){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Eagle(), cell.getRow(), cell.getCol());
        }
    }
}
