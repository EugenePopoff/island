package com.javarush.island.popov.thread.lifeTasks;

import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.essence.animal.abstraction.Animal;

import java.util.List;


public class MoveTask implements Runnable {
    @Override
    public void run() {
        // Получение списка животных на острове с положительной скоростью перемещения
        List<Animal> animals = Island.getInstance().getAllAnimals().stream().filter(c -> c.getStep() > 0).toList();

        // Проход по списку животных и перемещение каждого из них
        for (Animal animal : animals) {
            animal.move(); // Вызов метода перемещения для текущего животного
        }
    }
}
