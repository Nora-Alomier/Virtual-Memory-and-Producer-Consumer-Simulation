/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cs330.project.r;

/**
 *
 * @author wahaj
*/

public class SharedData {
    public static final int BUFFER_SIZE = 5;
    public static final int PAGE_SIZE = 4;
    public static final int MAX_PAGES = 10;

    public int[] buffer = new int[BUFFER_SIZE];
    public int in = 0, out = 0, count = 0;
    public int[] page_table = new int[MAX_PAGES];
    public int[] frame = new int[MAX_PAGES];
    public int page_faults = 0;

    public SharedData() {
        for (int i = 0; i < MAX_PAGES; i++) {
            page_table[i] = -1;
            frame[i] = -1;
        }
        for (int i = 0; i < BUFFER_SIZE; i++) {
            buffer[i] = -1;
        }
    }
}

