/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs330.project.r;

/**
 *
 * @author wahaj
 */
public class SemaphoreWrapper {
    private int value;

    public SemaphoreWrapper(int initial) {
        this.value = initial;
    }

    public synchronized void waitSem() throws InterruptedException {
        while (value == 0) {
            wait();
        }
        value--;
    }

    public synchronized void signalSem() {
        value++;
        notify();
    }
}

