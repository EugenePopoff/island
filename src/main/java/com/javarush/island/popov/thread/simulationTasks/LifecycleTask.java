package com.javarush.island.popov.thread.simulationTasks;


import com.javarush.island.popov.thread.lifeTasks.AnimalDieTask;
import com.javarush.island.popov.thread.lifeTasks.EatTask;
import com.javarush.island.popov.thread.lifeTasks.MoveTask;
import com.javarush.island.popov.thread.lifeTasks.ReproductionTask;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LifecycleTask implements Runnable {
    private final CountDownLatch latch; // Синхронизатор для ожидания завершения всех подзадач
    private final EatTask eatTask; // Задача по питанию
    private final ReproductionTask reproductionTask; // Задача по размножению

    private final AnimalDieTask dieTask;


    public LifecycleTask() {
        latch = new CountDownLatch(0); // Создание синхронизатора на 3 задачи
        eatTask = new EatTask(latch); // Инициализация задачи по питанию
        reproductionTask = new ReproductionTask(latch); // Инициализация задачи по размножению
        dieTask= new AnimalDieTask(latch);

    }

    @Override
    public void run() {
        ExecutorService executorService = Executors.newFixedThreadPool(3); // Создание пула потоков
        executorService.submit(reproductionTask); // Запуск задачи по размножению

        try {
            latch.await(); // Ожидание завершения всех подзадач
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(eatTask); // Запуск задачи по питанию и уменьшению веса


        try {
            latch.await(); // Ожидание завершения всех подзадач
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(dieTask);
        try {
            latch.await(); // Ожидание завершения всех подзадач
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.submit(new MoveTask()); // Запуск задачи по перемещению
    }

    public ReproductionTask getReproductionTask() {
        return reproductionTask; // Получение задачи по размножению
    }

    public EatTask getEatTask() {
        return eatTask; // Получение задачи по питанию
    }

    public AnimalDieTask getDieTask() {
        return  dieTask; // Получение задачи по питанию
    }
}
