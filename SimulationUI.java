/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs330.project.r;

/**
 *
 * @author wahaj
 */
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class SimulationUI extends JFrame {
    private JTextArea textArea;
    private JLabel statusLabel;

    public SimulationUI() {
        setTitle("OS Simulation Output");
        setSize(700, 450);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        statusLabel = new JLabel("Status: ");
        add(statusLabel, BorderLayout.SOUTH);
    }

    public void log(String message) {
        SwingUtilities.invokeLater(() -> {
            textArea.append(message + "\n");
        });
    }
        public void updateStatus(int[] buffer, int[] frame, int faults) {
        SwingUtilities.invokeLater(() -> {
            statusLabel.setText("Buffer: " + java.util.Arrays.toString(buffer)
                    + " | Disk: " + java.util.Arrays.toString(frame)
                    + " | Page Faults: " + faults);
        });
    }

    // ✅ Add this method:
    public JTextArea getTextArea() {
        return textArea;
    }


}

