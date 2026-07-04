/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs330.project.r;

/**
 *
 * @author wahaj
 */
public class Producer extends Thread {
    private final int id;
    private final SharedData shared;
    private final SemaphoreWrapper empty, full, mutex;
    private final MemoryManager memory;

    public Producer(int id, SharedData shared, SemaphoreWrapper empty, SemaphoreWrapper full, SemaphoreWrapper mutex, MemoryManager memory) {
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
                int item = (int) (Math.random() * 100); // produce random item

                memory.requestPage(item); // handle page request (includes swap if needed)

                empty.waitSem();
                mutex.waitSem();

                shared.buffer[shared.in] = item;
                shared.in = (shared.in + 1) % SharedData.BUFFER_SIZE;
                shared.count++;

                Logger.log("[Producer " + id + "] Inserted item: " + item + " into buffer.");

                memory.logSystemState();

                mutex.signalSem();
                full.signalSem();

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
