package com.javarush.island.popov.thread.lifeTasks;


import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class ReproductionTask implements Runnable {
    private int descendants;
    private final CountDownLatch latch;
    private List<Animal> animalsMultiplied; // Объявление как поле класса

    public ReproductionTask(CountDownLatch latch) {
        this.latch = latch;
        this.animalsMultiplied = new ArrayList<>(); // Инициализация списка
    }


    @Override
    public void run() {
        descendants = 0; // Обнуление счетчика созданных потомков
        List<Animal> animals = Island.getInstance().getAllAnimals(); // Получение списка всех животных на острове
        List<Animal> animalsMultiplied = new ArrayList<>(); // Создание списка животных, участвовавших в размножении


        if (animals.size() > 0) { // Проверка наличия животных на острове
            for (Animal currentAnimal : animals) {
                // Проверка, не участвовало ли текущее животное уже в размножении
                if (!animalsMultiplied.contains(currentAnimal)) {
                    Cell cell = Island.getInstance().getLocation(currentAnimal.getRow(), currentAnimal.getCol()); // Получение местоположения текущего животного
                    List<Animal> locationAnimals = cell.getAnimals(); // Получение списка животных в данной локации

                    if (locationAnimals.size() > 1) { // Проверка наличия других животных в той же локации
                        // Фильтрация списка животных в локации по имени и исключению текущего животного
                        locationAnimals = locationAnimals.stream().filter(c -> c.getName().equals(currentAnimal.getName()) && c != currentAnimal).toList();

                        if (locationAnimals.size() > 0) { // Проверка наличия партнера для размножения
                            Animal partner = locationAnimals.get(0); // Получение партнера для размножения

                            // Проверка, не участвовал ли партнер уже в размножении
                            if (!animalsMultiplied.contains(partner)) {
                                currentAnimal.reproduction(partner); // Вызов метода размножения для текущего животного

                                animalsMultiplied.add(currentAnimal); // Добавление текущего и партнера в список размноженных
                                animalsMultiplied.add(partner);

                                descendants++; // Увеличение счетчика созданных потомков

                                System.out.println("Родился: " + currentAnimal.getName()); // Вывод сообщения о создании потомка

                            }
                        }
                    }
                }
            }
        }
        latch.countDown(); // Уменьшение счетчика синхронизатора
    }

    public int getDescendants() {
        return descendants; // Получение количества созданных потомков
    }
}
