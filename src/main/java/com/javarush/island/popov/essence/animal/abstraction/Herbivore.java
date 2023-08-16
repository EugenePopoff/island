package com.javarush.island.popov.essence.animal.abstraction;


public abstract class Herbivore extends Animal {

    public Herbivore(double weight, int step, double kgForSaturation, int maxPopulation, String name) {
        super(weight, step, kgForSaturation, maxPopulation, name);
    }

    @Override
    public double getChanceToEat(String foodName) {
        return switch (foodName) {
            case "Plant" -> 1;
            default -> 0;
        };
    }
}