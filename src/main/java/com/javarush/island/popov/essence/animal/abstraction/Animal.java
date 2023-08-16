package com.javarush.island.popov.essence.animal.abstraction;

import com.javarush.island.popov.essence.plant.Plant;
import com.javarush.island.popov.error.ObjectNotLifeFormException;
import com.javarush.island.popov.essence.Essence;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Animal extends Essence {
    private int daysSinceLastMeal = 0;
    private final int step; // Скорость перемещения, не более чем, клеток за ход
    private final double kgForSaturation; // Максимальное количество килограммов пищи, необходимое животному для полного насыщения
    private double weight; // Текущий вес животного

    public Animal(double weight, int step, double kgForSaturation, int maxPopulation, String name) {
        super(weight, maxPopulation, name);
        this.step = step;
        this.kgForSaturation = kgForSaturation;
        this.weight = kgForSaturation; // Начальный вес животного устанавливается равным максимальному для насыщения
    }

    public abstract double getChanceToEat(String foodName); // Абстрактный метод для получения вероятности съесть объект

    public abstract void reproduction(Animal partner); // Абстрактный метод для размножения животных

    public int getStep() {
        return step; // Получение скорости перемещения животного
    }

    public double getWeight() {
        return weight; // Получение текущего веса животного
    }

    public void setWeight(double weight) {
        this.weight = weight; // Установка нового веса животного
    }

    public double getKgForSaturation() {
        return kgForSaturation; // Получение максимального веса для насыщения животного
    }

    public boolean eat(Object food) {
        double chanceToEat;
        Essence essence = null;
        boolean animalEatFood;

        if (food instanceof Essence) {
            essence = (Essence) food;
        } else {
            try {
                throw new ObjectNotLifeFormException("Объект не является животным/растением."); // Обработка случая, когда переданный объект не является живым существом
            } catch (Exception e) {
                System.out.println(e.getMessage()); // Вывод сообщения об ошибке в консоль
            }
        }

        String foodName = essence.getName(); // Получение имени съедобного объекта
        chanceToEat = getChanceToEat(foodName); // Получение вероятности съесть объект на основе его имени

        animalEatFood = ThreadLocalRandom.current().nextDouble() < chanceToEat; // Вычисление, съест ли животное объект, с учетом вероятности
        if (animalEatFood) {
            setWeight(Math.min((getWeight() + essence.getWeight()), getKgForSaturation())); // Увеличение веса животного, ограниченное максимальным весом для насыщения
            Cell cell = Island.getInstance().getLocation(essence.getRow(), essence.getCol()); // Получение местоположения объекта
            if (essence instanceof Animal animal) {
                if (cell.getAnimals().contains(animal)) {
                    Island.getInstance().removeAnimal(animal, cell.getRow(), cell.getCol()); // Удаление съеденного животного из ячейки острова
                } else {
                    return false; // Возврат false в случае, если животное не удалось удалить из ячейки
                }
            } else {
                Plant plant = (Plant) essence;
                if (cell.getPlants().size() > 0) {
                    Island.getInstance().removePlant(plant, cell.getRow(), cell.getCol()); // Удаление съеденного растения из ячейки острова
                } else {
                    return false; // Возврат false в случае, если растение не удалось удалить из ячейки
                }
            }
        }
        return animalEatFood; // Возврат значения, показывающего, съело ли животное объект
    }

    public void move() {
        Random random = new Random();
        int randomCells = random.nextInt(getStep()) + 1; // Генерация случайного числа шагов для перемещения
        int direction = random.nextInt(4); // Генерация случайного направления (0 - вверх, 1 - вниз, 2 - влево, 3 - вправо)
        int newRow = getRow();
        int newCol = getCol();

        // Вычисление новых координат в зависимости от направления
        switch (direction) {
            case 0 -> // Вверх
                    newRow -= randomCells;
            case 1 -> // Вниз
                    newRow += randomCells;
            case 2 -> // Влево
                    newCol -= randomCells;
            case 3 -> // Вправо
                    newCol += randomCells;
        }

        // Проверка, чтобы новые координаты не выходили за границы поля
        while (newRow < 0 || newRow >= Island.getInstance().getNumRow() || newCol < 0 || newCol >= Island.getInstance().getNumCol()) {
            randomCells = random.nextInt(getStep()) + 1;
            direction = random.nextInt(4);
            newRow = getRow();
            newCol = getCol();
            switch (direction) {
                case 0 -> // Вверх
                        newRow -= randomCells;
                case 1 -> // Вниз
                        newRow += randomCells;
                case 2 -> // Влево
                        newCol -= randomCells;
                case 3 -> // Вправо
                        newCol += randomCells;
            }
        }
        Island.getInstance().removeAnimal(this, getRow(), getCol()); // Удаление животного из текущей ячейки острова
        // Обновление координат
        setRow(newRow);
        setCol(newCol);
        Island.getInstance().addAnimal(this, newRow, newCol); // Добавление животного в новую ячейку острова
    }
}