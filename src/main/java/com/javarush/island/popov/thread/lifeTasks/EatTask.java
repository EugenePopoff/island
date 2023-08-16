package com.javarush.island.popov.thread.lifeTasks;

import com.javarush.island.popov.essence.Essence;
import com.javarush.island.popov.island.Island;
import com.javarush.island.popov.island.Cell;
import com.javarush.island.popov.essence.animal.abstraction.Animal;
import com.javarush.island.popov.essence.plant.Plant;
import com.javarush.island.popov.simulation.IslandSimulation;
import com.javarush.island.popov.thread.simulationTasks.StatTask;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;




public class EatTask implements Runnable {
    private int daysToDie = 25;
    private static List<Animal> failedToEatAnimals = new ArrayList<>();
    public List<String> eatenAnimalNames = new ArrayList<>(); // Список имён съеденных животных

    private final CountDownLatch latch; // Синхронизатор для ожидания завершения задач
    private int animalsEaten; // Количество съеденных животных


    public EatTask(CountDownLatch latch) {
        this.latch = latch; // Инициализация синхронизатора
    }

    @Override
    public void run() {
        animalsEaten = 0; // Обнуление количества съеденных животных
        Island islandCell = Island.getInstance(); // Получение экземпляра островной среды
        List<Animal> animals = islandCell.getAllAnimals(); // Получение списка всех животных
        int countAnimalsStart = animals.size(); // Запоминание начального количества животных на острове

        if (countAnimalsStart > 0 && !animals.isEmpty()) {
            // Проверка на наличие животных, отличных от "гусеницы"
            Iterator<Animal> iterator = animals.iterator();

            while (iterator.hasNext()) {
                Animal currentAnimal = iterator.next(); // Получение текущего животного из итератора
                if (currentAnimal.getKgForSaturation() > 0) {
                   Cell cell = islandCell.getLocation(currentAnimal.getRow(), currentAnimal.getCol()); // Получение местоположения текущего животного
                    List<Essence> locationEssences = cell.getEssence(); // Получение всех живых существ в данной локации

                    for (Essence essence : locationEssences) {
                        if (currentAnimal.getChanceToEat(essence.getName()) > 0 && tryToEat(islandCell, currentAnimal, essence)) {
                            System.out.println(currentAnimal.getName() + " съел(а) " + essence.getName());
                            break; // Попытка съесть, прерывание при успешном питании
                        }
                    }
                }
                iterator.remove(); // Удаление текущего животного из списка
            }
        } else if (countAnimalsStart == 0) {
            finalMessage("Всё кончено!", "животных", StatTask.getCurrentDay());
        }
        latch.countDown(); // Уменьшение счетчика синхронизатора
    }

    private boolean tryToEat(Island islandCell, Animal animal, Essence essence) {
        boolean isEaten = animal.eat(essence); // Попытка съесть живое существо
        if (isEaten && essence instanceof Animal) { // Если съедено и это животное
            removeEatenForm(islandCell, essence); // Удаление съеденного из локации
            animalsEaten++; // Увеличение счетчика съеденных животных
        }
        return isEaten; // Возвращение статуса успешного питания
    }

    public void removeEatenForm(Island islandCell, Essence essence) {
        Cell cell = islandCell.getLocation(essence.getRow(), essence.getCol()); // Получение местоположения съеденного
        if (essence instanceof Animal animalEaten) {
            eatenAnimalNames.add(animalEaten.getName()); // Добавление имени съеденного животного в список
           // System.out.println("Съедено: " + animalEaten.getName()); // Вывод имени съеденного животного
            islandCell.removeAnimal(animalEaten, cell.getRow(), cell.getCol()); // Удаление съеденного животного из локации
        } else if (essence instanceof Plant plant) {
            islandCell.removePlant(plant, cell.getRow(), cell.getCol()); // Удаление съеденного растения из локации
        }
    }

        private void finalMessage(String message, String creatureType, int currentDay) {
        System.out.printf("%s На острове не осталось живых %s на %d день!", message, creatureType, currentDay); // Вывод сообщения о конце игры
        IslandSimulation.getInstance().getExecutorService().shutdown(); // Остановка пула потоков
        System.exit(0); // Завершение работы программы
    }

    public int getAnimalsEaten() {
        return animalsEaten; // Получение количества съеденных животных
    }
}
