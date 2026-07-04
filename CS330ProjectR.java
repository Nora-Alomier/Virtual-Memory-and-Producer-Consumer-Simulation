package cs330.project.r;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
 import javax.swing.*;
import java.awt.*;
 
 
import javax.swing.*;
import javax.swing.*;



public class CS330ProjectR {

    public static void main(String[] args) {
        String[] options = {"FIFO", "LRU"};
        int choice = JOptionPane.showOptionDialog(null,
                "Select Page Replacement Policy:",
                "Choose Algorithm",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        // Set up GUI
SimulationUI ui = new SimulationUI();
ui.setVisible(true);

// 💡 This line now works because of the method you just added:
JTextArea logArea = ui.getTextArea();
Logger.setTextArea(logArea);          // ✅ now GUI receives log messages


        // Init shared objects
        SharedData shared = new SharedData();
        SemaphoreWrapper empty = new SemaphoreWrapper(SharedData.BUFFER_SIZE);
        SemaphoreWrapper full = new SemaphoreWrapper(0);
        SemaphoreWrapper mutex = new SemaphoreWrapper(1);

        // Memory Manager with FIFO or LRU
        String selectedPolicy = choice == 0 ? "FIFO" : "LRU";
        MemoryManager memory = new MemoryManager(shared, selectedPolicy, logArea);

        // Start multiple producers and consumers (if desired)
        for (int i = 1; i <= 1; i++) {
            new Producer(i, shared, empty, full, mutex, memory).start();
            new Consumer(i, shared, empty, full, mutex, memory).start();
        }
    }
}
