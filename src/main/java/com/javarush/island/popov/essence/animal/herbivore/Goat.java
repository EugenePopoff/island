package com.javarush.island.popov.essence.animal.herbivore;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Goat extends Herbivore {

    public Goat() {
        super(60, 3, 10, 140, "Goat");
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Goat){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Goat(), cell.getRow(), cell.getCol());
        }
    }
}
