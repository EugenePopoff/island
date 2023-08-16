package com.javarush.island.popov.essence.animal.herbivore;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Boar extends Herbivore {

    public Boar() {
        super(400, 2, 50, 50, "Boar");
    }


    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Mouse" -> 0.5;
            case "Caterpillar" -> 0.9;
            case "Plant" -> 1;
            default -> 0;
        };
    }


    public void reproduction(Animal partner) {
        if (partner instanceof Boar) { // Проверка, является ли партнер животного экземпляром класса Boar (кабан)
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol()); // Получение местоположения партнера
            Island.getInstance().addAnimal(new Boar(), cell.getRow(), cell.getCol()); // Добавление нового экземпляра класса Boar в ту же ячейку острова, где находится партнер
        }
    }
}