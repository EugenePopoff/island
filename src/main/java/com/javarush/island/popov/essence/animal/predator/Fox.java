package com.javarush.island.popov.essence.animal.predator;

import com.javarush.island.popov.essence.animal.abstraction.Predator;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Fox extends Predator {

    public Fox() {
        super(8, 2, 2, 30, "Fox");
    }


    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Caterpillar" -> 0.4;
            case "Duck" -> 0.6;
            case "Rabbit" -> 0.7;
            case "Mouse" -> 0.9;
            default -> 0;
        };
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Fox){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Fox(), cell.getRow(), cell.getCol());
        }
    }
}
