package com.javarush.island.popov.main;


import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.simulation.IslandSimulation;

public class Start {

    public void startSimulation() {
        Island.getInstance().initializeLocations();
        IslandSimulation.getInstance().createIsland();
    }
}

