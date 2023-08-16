package com.javarush.island.popov.essence.animal.herbivore;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Sheep extends Herbivore {

    public Sheep() {
        super(70, 3, 15, 140, "Sheep");
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Sheep){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Sheep(), cell.getRow(), cell.getCol());
        }
    }
}
