package com.javarush.island.popov.essence.animal.herbivore;
import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Duck extends Herbivore {

    public Duck() {
        super(1, 4, 0.15, 200, "Duck");
    }


    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Caterpillar" -> 0.9;
            case "Plant" -> 1;
            default -> 0;
        };
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Duck){
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol());
            Island.getInstance().addAnimal(new Duck(), cell.getRow(), cell.getCol());
        }
    }
}
