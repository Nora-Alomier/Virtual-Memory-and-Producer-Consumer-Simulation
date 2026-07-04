/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs330.project.r;

import java.io.*;
import java.io.IOException;

/**
 *
 * @author wahaj
 */

 import java.io.*;

import java.io.*;
import java.util.*;

import java.io.*;

public class BackingStore {
    private File diskFile = new File("backingStore.txt");  // Use relative path or specify absolute path

    // Simulate writing a page to disk (eviction)
    public void writePage(int pageID) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(diskFile, true))) {
            writer.write("Page " + pageID + " swapped OUT to disk.\n");
        } catch (IOException e) {
            System.err.println("Error writing page to disk: " + e.getMessage());
        }
    }

    // Simulate reading a page from disk (loading into memory)
    public void readPage(int pageID) {
        try (BufferedReader reader = new BufferedReader(new FileReader(diskFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Add logic for processing the file contents (if needed)
                System.out.println(line);  // For debugging, you can print or process the content here
            }
        } catch (IOException e) {
            System.err.println("Error reading page from disk: " + e.getMessage());
        }
    }
}






