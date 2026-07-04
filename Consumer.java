/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs330.project.r;

/**
 *
 * @author wahaj
 */
public class Consumer extends Thread {
    private final int id;
    private final SharedData shared;
    private final SemaphoreWrapper empty, full, mutex;
    private final MemoryManager memory;

    public Consumer(int id, SharedData shared, SemaphoreWrapper empty, SemaphoreWrapper full, SemaphoreWrapper mutex, MemoryManager memory) {
        this.id = id;
        this.shared = shared;
        this.empty = empty;
        this.full = full;
        this.mutex = mutex;
        this.memory = memory;
    }

    public void run() {
        while (true) {
            try {
                full.waitSem();
                mutex.waitSem();

                int item = shared.buffer[shared.out];
                shared.out = (shared.out + 1) % SharedData.BUFFER_SIZE;
                shared.count--;

                memory.requestPage(item); // handle page access

                Logger.log("[Consumer " + id + "] Consumed item: " + item + " from buffer.");

                memory.logSystemState();

                mutex.signalSem();
                empty.signalSem();

                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
