
package cs330.project.r;

import java.util.*;
import javax.swing.*;

public class MemoryManager {
    private SharedData shared;
    private String policy;
    private Map<Integer, Integer> pageTable;
    private Queue<Integer> frameQueue;
    private JTextArea logArea;
    private BackingStore disk = new BackingStore();

    private Set<Integer> usedFrames = new HashSet<>();

    public MemoryManager(SharedData shared, String policy, JTextArea logArea) {
        this.shared = shared;
        this.policy = policy;
        this.pageTable = new HashMap<>();
        this.frameQueue = new LinkedList<>();
        this.logArea = logArea;
    }

    public void requestPage(int item) {
        int pageID = item / SharedData.PAGE_SIZE;

        // Check if page is already in memory
        if (usedFrames.contains(pageID)) {
            logToConsole("Page " + pageID + " is already in RAM.");
            return;
        }

        // Page fault
        shared.page_faults++;

        if (usedFrames.size() >= SharedData.MAX_PAGES) {
            int evictedPage = frameQueue.poll();
            disk.writePage(evictedPage);
            usedFrames.remove(evictedPage);
            logToConsole("[Eviction] Page " + evictedPage + " evicted to disk.");
        }

        // Load new page
        disk.readPage(pageID);
        usedFrames.add(pageID);
        frameQueue.add(pageID);

        // Insert item into circular buffer
        shared.buffer[shared.in % SharedData.BUFFER_SIZE] = item;
        shared.in = (shared.in + 1) % SharedData.BUFFER_SIZE;

        if (policy.equals("LRU")) {
            pageTable.put(pageID, 0);  // Simplified LRU tracking
        }

        updateMemoryUsage();
        logToConsole("Page " + pageID + " loaded into memory.");
    }

    private void updateMemoryUsage() {
        Arrays.fill(shared.frame, -1);
        int i = 0;
        for (int pageID : usedFrames) {
            if (i < shared.frame.length) {
                shared.frame[i++] = pageID;
            }
        }
    }

    private void logToConsole(String message) {
        Logger.log(message);
        logArea.append(message + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    public void logSystemState() {
        logToConsole("[Buffer State] " + Arrays.toString(shared.buffer));
        logToConsole("[Memory Usage] " + Arrays.toString(shared.frame));
        logToConsole("[Page Faults] " + shared.page_faults);
    }
}
