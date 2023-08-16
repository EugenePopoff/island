package com.javarush.island.popov.essence.animal.herbivore;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Caterpillar extends Herbivore {

    public Caterpillar() {
        super(0.01, 0, 0, 1000, "Caterpillar");
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Caterpillar){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Caterpillar(), cell.getRow(), cell.getCol());
        }
    }
}
