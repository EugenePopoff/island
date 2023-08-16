package com.javarush.island.popov.thread.simulationTasks;

import com.javarush.island.popov.simulation.IslandSimulation;


public class PlantGrowthTask implements Runnable {

    public void run() {
        int countPlants = 100; // Заданное количество растений

        IslandSimulation islandSimulation = IslandSimulation.getInstance();

        if (islandSimulation.getTimeNow() >= 2) { // Если текущее время в симуляции больше или равно 2
            islandSimulation.placePlants(countPlants * 3); // Размещение растений
        } else {
            islandSimulation.placePlants(countPlants); // Иначе размещение заданного количества растений
        }
    }
}
