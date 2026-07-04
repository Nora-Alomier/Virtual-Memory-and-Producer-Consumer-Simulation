package cs330.project.r;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author wahaj
 */
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.swing.*;

public class Logger {
    private static final String LOG_FILE = "simulation_log.txt";
    private static boolean initialized = false;
    private static JTextArea guiTextArea; // 🌟 This links to your GUI window

    // 🌟 This method lets you plug in the GUI text area
    public static void setTextArea(JTextArea textArea) {
        guiTextArea = textArea;
    }

    public static void log(String message) {
        String timestamp = "[" + LocalDateTime.now() + "] ";
        String fullMessage = timestamp + message;

        // Print to NetBeans console
        System.out.println(fullMessage);

        // Print to the GUI window ✨
        if (guiTextArea != null) {
            SwingUtilities.invokeLater(() -> {
                guiTextArea.append(fullMessage + "\n");
                guiTextArea.setCaretPosition(guiTextArea.getDocument().getLength());
            });
        }

        // Save to simulation_log.txt
        try (FileWriter writer = new FileWriter(LOG_FILE, !initialized)) {
            writer.write(fullMessage + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        initialized = true;
    }
}
