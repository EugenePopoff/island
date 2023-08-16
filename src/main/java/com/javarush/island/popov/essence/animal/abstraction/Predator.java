package com.javarush.island.popov.essence.animal.abstraction;


public abstract class Predator extends Animal {
    public Predator(double weight, int step, double kgForSaturation, int maxPopulation, String name) {
        super(weight, step, kgForSaturation, maxPopulation, name);
    }
}