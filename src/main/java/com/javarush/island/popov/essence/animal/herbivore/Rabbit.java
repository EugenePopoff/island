package com.javarush.island.popov.essence.animal.herbivore;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Rabbit extends Herbivore {

    public Rabbit() {
        super(2, 2, 0.45, 150, "Rabbit");
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Rabbit){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Rabbit(), cell.getRow(), cell.getCol());
        }
    }
}
