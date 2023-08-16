package com.javarush.island.popov.island;

import com.javarush.island.popov.essence.animal.abstraction.Animal;
import com.javarush.island.popov.essence.plant.Plant;

import java.util.ArrayList;
import java.util.List;

public class Island {
    private Cell[][] cells; // Двумерный массив состоящий из локаций(ячеек)
    private final int numRow = 50; // Количество строк по умолчанию
    private final int numCol = 10; // Количество столбцов по умолчанию
    private static volatile Island instance; // Статический экземпляр класса Island для реализации Singleton

//    private Island() {
//        // Приватный конструктор, чтобы избежать создания экземпляров класса извне
//    }

    public int getNumRow() {
        return numRow; // Получение количества строк на острове
    }

    public int getNumCol() {
        return numCol; // Получение количества столбцов на острове
    }

    public synchronized Cell getLocation(int row, int column) {
        return cells[row][column]; // Получение локации по заданным координатам
    }

    public static Island getInstance() {
        if (instance == null) {
            synchronized (Island.class) {
                if (instance == null) {
                    instance = new Island(); // Создание нового экземпляра Island при первом вызове getInstance()
                }
            }
        }
        return instance; // Возврат существующего экземпляра Island
    }

    public void initializeLocations() {
        System.out.println("Загрузка симуляции острова...");
        cells = new Cell[numRow][numCol]; // Создание массива локаций с количеством строк и столбцов по умолчанию
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new Cell(i, j); // Инициализация каждой локации в массиве
            }
        }
    }

    public void addAnimal(Animal animal, int row, int column) {
        Cell cell = getLocation(row, column); // Получение локации по координатам
        cell.addAnimal(animal); // Добавление животного в локацию
    }

    public void removeAnimal(Animal animal, int row, int column) {
        Cell cell = getLocation(row, column); // Получение локации по координатам
        cell.removeAnimal(animal); // Удаление животного из локации
    }

    public void addPlant(Plant plant, int row, int column) {
        Cell cell = getLocation(row, column); // Получение локации по координатам
        cell.addPlant(plant); // Добавление растения в локацию
    }

    public void removePlant(Plant plant, int row, int column) {
        Cell cell = getLocation(row, column); // Получение локации по координатам
        cell.removePlant(plant); // Удаление растения из локации
    }

    public synchronized List<Animal> getAllAnimals() {
        List<Animal> allAnimals = new ArrayList<>();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                allAnimals.addAll(cell.getAnimals()); // Получение всех животных со всех локаций
            }
        }
        return allAnimals; // Возврат списка всех животных
    }

    public List<Plant> getAllPlants() {
        List<Plant> allPlants = new ArrayList<>();
        for (Cell[] row : cells) {
            for (Cell cell : row) {
                allPlants.addAll(cell.getPlants()); // Получение всех растений со всех локаций
            }
        }
        return allPlants; // Возврат списка всех растений
    }
}
