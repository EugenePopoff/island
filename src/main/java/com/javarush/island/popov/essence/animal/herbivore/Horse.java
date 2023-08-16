package com.javarush.island.popov.essence.animal.herbivore;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Horse extends Herbivore {

    public Horse() {
        super(400, 4, 60, 20, "Horse");
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Horse){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Horse(), cell.getRow(), cell.getCol());
        }
    }
}
