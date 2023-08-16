package com.javarush.island.popov.essence;

public class Essence {
    private final double weight; // Вес животного/растения в кг
    private final int maxPopulation; // Максимальное количество вида животного/растения на 1 клетке
    private final String name; // Имя животного/растения
    private int row; // Текущая строка, в которой находится объект
    private int col; // Текущая колонка, в которой находится объект

    public Essence(double weight, int maxPopulation, String name) {
        this.weight = weight; // Инициализация веса объекта при создании
        this.maxPopulation = maxPopulation; // Инициализация максимального количества вида на 1 клетке при создании
        this.name = name; // Инициализация имени объекта при создании
    }

    public double getWeight() {
        return weight; // Получение веса объекта
    }

    public int getMaxPopulation() {
        return maxPopulation; // Получение максимального количества вида на 1 клетке
    }

    public String getName() {
        return name; // Получение имени объекта
    }

    public int getRow() {
        return row; // Получение текущей строки, в которой находится объект
    }

    public int getCol() {
        return col; // Получение текущей колонки, в которой находится объект
    }

    public void setRow(int row) {
        this.row = row; // Установка новой строки для объекта
    }

    public void setCol(int col) {
        this.col = col; // Установка новой колонки для объекта
    }
}