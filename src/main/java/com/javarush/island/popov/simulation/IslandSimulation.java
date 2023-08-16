
package com.javarush.island.popov.simulation;

import com.javarush.island.popov.essence.animal.abstraction.Herbivore;
import com.javarush.island.popov.essence.animal.abstraction.Predator;
import com.javarush.island.popov.essence.animal.herbivore.*;
import com.javarush.island.popov.essence.animal.predator.*;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;

import com.javarush.island.popov.essence.plant.Plant;
import com.javarush.island.popov.thread.simulationTasks.LifecycleTask;
import com.javarush.island.popov.thread.simulationTasks.PlantGrowthTask;
import com.javarush.island.popov.thread.simulationTasks.StatTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


public class IslandSimulation {


    private volatile int currentDay = 0; // Счетчик текущего дня
    private final long startTime;
    private final int countHerbivores = 150;
    private final int countPlants = 1500;
    private final int countPredators = 50;
    private static volatile IslandSimulation instance;
    private volatile ScheduledExecutorService executorService;

    private IslandSimulation() {
        startTime = System.currentTimeMillis();
    }


    public static IslandSimulation getInstance() {
        if (instance == null) {
            synchronized (IslandSimulation.class) {
                if (instance == null) {
                    instance = new IslandSimulation();
                }
            }
        }
        return instance;
    }

    public void createIsland() {
        placeHerbivores(countHerbivores);
        placePredators(countPredators);
        placePlants(countPlants);

        runIslandLife();
    }


    private void runIslandLife() {
        executorService = Executors.newScheduledThreadPool(3); // Создание планировщика на 3 потока

        LifecycleTask lifecycleTask = new LifecycleTask(); // Создание задачи жизненного цикла
        PlantGrowthTask plantGrowthTask = new PlantGrowthTask(); // Создание задачи роста растений
        StatTask statTask = new StatTask(lifecycleTask.getEatTask(), lifecycleTask.getReproductionTask(),lifecycleTask.getDieTask()); // Создание задачи сбора статистики

        executorService.scheduleAtFixedRate(lifecycleTask, 1, 8, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(plantGrowthTask, 24, 16, TimeUnit.SECONDS);
        executorService.scheduleAtFixedRate(statTask, 0, 8, TimeUnit.SECONDS);

        // Увеличиваем счетчик дней с определенной периодичностью
        executorService.scheduleAtFixedRate(() -> currentDay++, 0, 8, TimeUnit.SECONDS);
    }

    private List<Herbivore> createHerbivores(int countHerbivores) {
        List<Herbivore> herbivores = new ArrayList<>();
        Random random = new Random();

        // Создаем по одному животному каждого вида
        herbivores.add(new Buffalo());
        herbivores.add(new Caterpillar());
        herbivores.add(new Deer());
        herbivores.add(new Duck());
        herbivores.add(new Goat());
        herbivores.add(new Horse());
        herbivores.add(new Mouse());
        herbivores.add(new Rabbit());
        herbivores.add(new Sheep());
        herbivores.add(new Boar());

        // Генерируем случайное количество животных каждого вида, не менее 1
        int remainingCount = countHerbivores - herbivores.size();
       // int remainingCount=10;
        for (int i = 0; i < remainingCount; i++) {
            // Генерируем случайный индекс для выбора вида животного
            int randomIndex = random.nextInt(herbivores.size());
            Herbivore randomHerbivore = herbivores.get(randomIndex);
            try {
                // Создаем экземпляр животного через рефлексию
                Herbivore newHerbivore = randomHerbivore.getClass().newInstance();
                herbivores.add(newHerbivore);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return herbivores;
    }


    private List<Predator> createPredators(int countPredators) {
        List<Predator> predators = new ArrayList<>();
        Random random = new Random();

        // Создаем по одному животному каждого вида
        predators.add(new Bear());
        predators.add(new Eagle());
        predators.add(new Fox());
        predators.add(new Python());
        predators.add(new Wolf());

        // Генерируем случайное количество животных каждого вида, не менее 1
        int remainingCount = countPredators - predators.size();
        //int remainingCount = 5;
        for (int i = 0; i < remainingCount; i++) {
            // Генерируем случайный индекс для выбора вида животного
            int randomIndex = random.nextInt(predators.size());
            Predator randomPredator = predators.get(randomIndex);
            try {
                // Создаем экземпляр животного через рефлексию
                Predator newPredator = randomPredator.getClass().newInstance();
                predators.add(newPredator);
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return predators;
    }


    private List<Plant> createPlants(int countPlants) {
        List<Plant> plants = new ArrayList<>();
        for (int i = 0; i < countPlants; i++) {
            plants.add(new Plant());
        }
        return plants;
    }


    public void placeHerbivores(int countHerbivores) {
        List<Herbivore> herbivores = createHerbivores(countHerbivores); // Создание списка травоядных с заданным количеством
        Random random = ThreadLocalRandom.current(); // Получение генератора случайных чисел

        for (Herbivore herbivore : herbivores) { // Перебор всех травоядных из списка
            boolean placed = false; // Флаг, определяющий, размещено ли животное на ячейке
            while (!placed) { // Пока животное не размещено
                int row = random.nextInt(Island.getInstance().getNumRow()); // Генерация случайной строки
                int column = random.nextInt(Island.getInstance().getNumCol()); // Генерация случайной колонки
                Cell cell = Island.getInstance().getLocation(row, column); // Получение локации (ячейки) по координатам

                // Проверка, что количество травоядных данного вида в ячейке не превышает максимальное
                if (cell.getAnimals().stream().filter(c -> c.getName().equals(herbivore.getName())).toList().size() <= herbivore.getMaxPopulation()) {
                    Island.getInstance().addAnimal(herbivore, row, column); // Добавление животного в ячейку
                    placed = true; // Установка флага, что животное размещено
                }
            }
        }
    }


    public void placePredators(int countPredators) {
        List<Predator> predators = createPredators(countPredators); // Создание списка хищников с заданным количеством
        Random random = ThreadLocalRandom.current(); // Получение генератора случайных чисел

        for (Predator predator : predators) { // Перебор всех хищников из списка
            boolean placed = false; // Флаг, определяющий, размещено ли животное на ячейке
            while (!placed) { // Пока животное не размещено
                int row = random.nextInt(Island.getInstance().getNumRow()); // Генерация случайной строки
                int column = random.nextInt(Island.getInstance().getNumCol()); // Генерация случайной колонки
                Cell cell = Island.getInstance().getLocation(row, column); // Получение локации (ячейки) по координатам

                // Проверка, что количество хищников данного вида в ячейке не превышает максимальное
                if (cell.getAnimals().stream().filter(c -> c.getName().equals(predator.getName())).toList().size() <= predator.getMaxPopulation()) {
                    Island.getInstance().addAnimal(predator, row, column); // Добавление хищника в ячейку
                    placed = true; // Установка флага, что животное размещено
                }
            }
        }
    }


    public void placePlants(int countPlants) {
        List<Plant> plants = createPlants(countPlants); // Создание списка растений с заданным количеством
        Random random = ThreadLocalRandom.current(); // Получение генератора случайных чисел

        for (Plant plant : plants) { // Перебор всех растений из списка
            boolean placed = false; // Флаг, определяющий, размещено ли растение на ячейке
            while (!placed) { // Пока растение не размещено
                int row = random.nextInt(Island.getInstance().getNumRow()); // Генерация случайной строки
                int column = random.nextInt(Island.getInstance().getNumCol()); // Генерация случайной колонки
                Cell cell = Island.getInstance().getLocation(row, column); // Получение локации (ячейки) по координатам

                // Проверка, что количество растений в ячейке не превышает максимальное
                if (cell.getPlants().size() <= plant.getMaxPopulation()) {
                    Island.getInstance().addPlant(plant, row, column); // Добавление растения в ячейку
                    placed = true; // Установка флага, что растение размещено
                }
            }
        }
    }

    public long getTimeNow() {
        return (System.currentTimeMillis() - startTime) / 1000; // Возвращает текущее время выполнения симуляции в секундах
    }

    public ScheduledExecutorService getExecutorService() {
        return executorService; // Возвращает экземпляр планировщика задач для управления потоками
    }
}