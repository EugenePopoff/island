package com.javarush.island.popov.essence.animal.herbivore;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Deer extends Herbivore {

    public Deer() {
        super(300, 4, 50, 20, "Deer");
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Deer){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Deer(), cell.getRow(), cell.getCol());
        }
    }
}
