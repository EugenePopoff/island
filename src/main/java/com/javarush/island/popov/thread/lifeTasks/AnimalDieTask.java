package com.javarush.island.popov.thread.lifeTasks;

import com.javarush.island.popov.essence.animal.predator.*;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;
import com.javarush.island.popov.simulation.IslandSimulation;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class AnimalDieTask implements Runnable {
    private int animalsDie;
    private int daysToDieOne;
    private int daysToDieTwo;
    private int daysToDieTree;

    private final CountDownLatch latch;


    public AnimalDieTask(CountDownLatch latch) {
        this.latch = latch;
    }


    @Override
    public void run() {
        animalsDie = 0; // Обнуление количества сдохших животных
        daysToDieOne = 24;
        daysToDieTwo = 240;
        daysToDieTree = 300;
        Island islandCell = Island.getInstance();
        List<Animal> animals = islandCell.getAllAnimals();

        for (Animal animal : animals) {
            double removalProbability;
            if (isPredator(animal)) {
                removalProbability = 0.55; // Вероятность удаления для хищников: %
            } else {
                removalProbability = 0.2; // Вероятность удаления для травоядных: %

                // Проверяем, если текущее время больше или равно daysToDieTwo
                if (IslandSimulation.getInstance().getTimeNow() >= daysToDieTwo) {
                    // Умножаем вероятность удаления для травоядных на 3
                    removalProbability *= 3;
                }
                if (IslandSimulation.getInstance().getTimeNow() >= daysToDieTree) {
                    removalProbability /= 1.2;

                }
            }

            if (IslandSimulation.getInstance().getTimeNow() >= daysToDieOne && Math.random() < removalProbability) {
                Cell cell = islandCell.getLocation(animal.getRow(), animal.getCol());
                boolean shouldRemove = Math.random() < removalProbability;

                if (shouldRemove) {
                    islandCell.removeAnimal(animal, cell.getRow(), cell.getCol());
                    System.out.println(animal.getName() + " сдох(ла) в столь свирепой среде.");
                    animalsDie++;
                }
            }
        }

        latch.countDown();
    }

    private boolean isPredator(Animal animal) {
        List<Class<? extends Animal>> predators = Arrays.asList(
                Bear.class, Eagle.class, Fox.class, Python.class, Wolf.class
        );
        return predators.contains(animal.getClass());
    }

    public int getAnimalsDie() {
        return animalsDie; // Получение количества съеденных животных
    }
}