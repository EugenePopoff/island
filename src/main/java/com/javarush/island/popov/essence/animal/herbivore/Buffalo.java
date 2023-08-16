package com.javarush.island.popov.essence.animal.herbivore;
import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

public class Buffalo extends Herbivore {

    public Buffalo() {
        super(700, 3, 100, 10, "Buffalo");
    }


    @Override
    public void reproduction(Animal partner) {
        if (partner instanceof Buffalo){ // Проверка, является ли партнер животного экземпляром класса Buffalo (буйвол)
            Cell cell = Island.getInstance().getLocation(partner.getRow(), partner.getCol()); // Получение местоположения партнера
            Island.getInstance().addAnimal(new Buffalo(), cell.getRow(), cell.getCol()); // Добавление нового экземпляра класса Buffalo в ту же ячейку острова, где находится партнер
        }
    }



}
