package com.javarush.island.popov.island;

import com.javarush.island.popov.essence.Essence;
import com.javarush.island.popov.essence.animal.abstraction.Animal;
import com.javarush.island.popov.essence.plant.Plant;

import java.util.*;

public class Cell {
    private final int row; // Строка, в которой расположена данная клетка
    private final int col; // Столбец, в котором расположена данная клетка
    private final List<Animal> animals; // Список животных, находящихся в данной клетка
    private final List<Plant> plants; // Список растений, находящихся в данной клетка

    public Cell(int row, int col) {
        this.row = row; // Инициализация строки данной клетка
        this.col = col; // Инициализация столбца данной клетка

        animals = new ArrayList<>(); // Инициализация списка животных
        plants = new ArrayList<>(); // Инициализация списка растений
    }
    public int getRow() {
        return row; // Получение строки данной клетка
    }

    public int getCol() {
        return col; // Получение столбца данной клетка
    }
    public List<Animal> getAnimals() {
        return animals; // Получение списка животных в данной клетка
    }
    public List<Plant> getPlants() {
        return plants; // Получение списка растений в данной клетка
    }


    public void addAnimal(Animal animal) {
        animal.setRow(row); // Установка строки для переданного животного
        animal.setCol(col); // Установка столбца для переданного животного

        animals.add(animal); // Добавление животного в список животных данной клетка
    }

    public void removeAnimal(Animal animal) {
        animals.remove(animal); // Удаление животного из списка животных данной клетка
    }

    public void addPlant(Plant plant) {
        plant.setRow(row); // Установка строки для переданного растения
        plant.setCol(col); // Установка столбца для переданного растения
        plants.add(plant); // Добавление растения в список растений данной клетка
    }

    public void removePlant(Plant plant) {
        plants.remove(plant); // Удаление растения из списка растений данной клетка
    }


    public List<Essence> getEssence() {
        List<Essence> essences = new ArrayList<>();
        essences.addAll(animals);
        essences.addAll(plants);
        return essences; // Получение списка сущностей (животных и растений) в данной клетка
    }
}
