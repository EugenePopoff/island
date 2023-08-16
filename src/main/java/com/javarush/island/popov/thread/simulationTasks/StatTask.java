package com.javarush.island.popov.thread.simulationTasks;


import com.javarush.island.popov.essence.animal.abstraction.Animal;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.thread.lifeTasks.AnimalDieTask;
import com.javarush.island.popov.thread.lifeTasks.EatTask;
import com.javarush.island.popov.thread.lifeTasks.ReproductionTask;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatTask implements Runnable {


    private final AnimalDieTask animalsDieTask;
    private int descendants;
    private int animalsEaten;
    private int animalsDie;
    private int countAnimalsEnd;
    private int countPlants;
    private static int currentDay = 0;
    private final ReproductionTask reproductionTask;
    private final EatTask eatTask;


    public StatTask(EatTask eatTask, ReproductionTask reproductionTask, AnimalDieTask animalDieTask) {
        this.eatTask = eatTask;
        this.reproductionTask = reproductionTask;
        this.animalsDieTask = animalDieTask;

    }


    @Override
    public void run() {
        descendants = reproductionTask.getDescendants();
        countAnimalsEnd = Island.getInstance().getAllAnimals().size(); // Обновляем количество животных на острове
        animalsEaten = eatTask.getAnimalsEaten();
        animalsDie = animalsDieTask.getAnimalsDie();
        countPlants = Island.getInstance().getAllPlants().size();
        printStats();
    }


    private void printStats() {
        System.out.println();
        System.out.println();
        System.out.printf("{--- ДЕНЬ %d ---}", currentDay);
        currentDay++;
        System.out.println();

        System.out.println();
        System.out.println("СТАТИСТИКА ПО ОСТРОВУ");
        System.out.println();

        System.out.print("Количество животных на острове: ");
        System.out.println(countAnimalsEnd);

        System.out.print("Количество умерших животных на острове: ");
        System.out.println(animalsDie);

        System.out.print("Количество съеденных животных на острове: ");
        System.out.println(animalsEaten);

        System.out.print("Количество родившихся животных на острове: ");
        System.out.println(descendants);

        System.out.print("Количество растений на острове: ");
        System.out.println(countPlants);


        System.out.println();

        Map<String, Integer> animalCountMap = countAnimalsByType(Island.getInstance().getAllAnimals());
        for (Map.Entry<String, Integer> entry : animalCountMap.entrySet()) {
            String animalType = entry.getKey();
            int animalCount = entry.getValue();
            System.out.println("Количество " + animalType + ": " + animalCount);
        }

        System.out.println("----------------------------------");
        System.out.println();
        System.out.println("Подробная статистика по всем животным:");

    }

    private Map<String, Integer> countAnimalsByType(List<Animal> animals) {
        Map<String, Integer> animalCountMap = new HashMap<>();

        for (Animal animal : animals) {
            String animalType = animal.getClass().getSimpleName(); // Получаем имя класса животного
            animalCountMap.put(animalType, animalCountMap.getOrDefault(animalType, 0) + 1);
        }

        return animalCountMap;
    }


    public static int getCurrentDay() {
        return currentDay;
    }
}

