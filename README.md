# Virtual Memory & Producer-Consumer Simulation

A Java Swing application that simulates two core operating system concepts side by side: virtual memory paging (FIFO and LRU replacement) and the classic producer-consumer synchronization problem. Built for the CS330 (Introduction to Operating Systems) course project at Prince Sultan University.

Rather than treating these as two separate demos, the simulation ties them together: every item a producer or consumer touches is also treated as a page request, so you can watch synchronization and memory management happening in the same run, in real time, through a live GUI.

## What it simulates

**Producer-Consumer synchronization**
Producer and consumer threads share a fixed-size circular buffer. Access is controlled through a custom semaphore implementation built from scratch on top of Java's `wait()`/`notify()` — not `java.util.concurrent.Semaphore` — specifically so the mutual-exclusion and signaling logic is visible rather than hidden behind a library call.

**Virtual memory paging**
Every produced or consumed item maps to a page ID. The memory manager tracks which pages are currently loaded, triggers a page fault when a requested page isn't in memory, and evicts a page when memory is full — using either:
- **FIFO** — evicts whichever page has been in memory the longest, regardless of how often it's used
- **LRU** — evicts whichever page hasn't been accessed most recently

Evicted pages are "swapped out" to a simulated backing store, which logs to `backing_store_log.txt` and displays its current contents in the GUI.

**Live GUI**
The whole simulation runs inside a Swing window showing the system log, buffer contents, memory frame usage, running page fault and replacement counts, and a Pause/Continue control — plus a startup dialog to choose FIFO or LRU before the run begins.

## Architecture

The project follows a layered design:

| Layer | Responsibility | Key class |
|---|---|---|
| User Interface | Displays simulation state, handles pause/resume | `SimulationUI` |
| Application | Page replacement logic, coordinates producer/consumer | `MemoryManager` |
| Concurrency | Thread synchronization, mutual exclusion | `SemaphoreWrapper` |
| Data | Shared state: buffer, page table, frames, counters | `SharedData` |

```
User Interface Layer  →  SimulationUI
Application Layer     →  MemoryManager (FIFO / LRU)
Concurrency Layer     →  SemaphoreWrapper
Data Layer             →  SharedData (buffer, page table, frames, page faults)
```

## Classes

| Class | Purpose |
|---|---|
| `CS330ProjectR.java` | Entry point — prompts for FIFO/LRU, wires up shared state, semaphores, and starts the producer/consumer threads |
| `SharedData.java` | Holds the circular buffer, page table, frame array, and fault/replacement counters |
| `SemaphoreWrapper.java` | Hand-rolled counting semaphore (`waitSem` / `signalSem`) used for empty/full/mutex control |
| `Producer.java` | Generates random items, requests the matching page, inserts into the buffer |
| `Consumer.java` | Removes items from the buffer, re-requests the page, frees the slot |
| `MemoryManager.java` | Handles page requests, detects faults, applies FIFO or LRU eviction, updates the GUI |
| `BackingStore.java` | Simulates disk — writes/reads evicted pages, persists to `backing_store_log.txt` |
| `Logger.java` | Timestamps every event to the console, the GUI log, and `simulation_log.txt` |
| `SimulationUI.java` | Builds and updates the Swing window: log, buffer, memory, fault/replacement counts, pause control |

## Technologies

- **Language:** Java
- **GUI:** Java Swing
- **Concurrency:** Java Threads, a custom semaphore built on `synchronized` / `wait()` / `notify()`
- **IDE:** NetBeans

## Running it

Open the project in NetBeans (or compile the `cs330.project.r` package with any Java IDE) and run `CS330ProjectR.java`. A dialog will ask you to choose between FIFO and LRU before the simulation window opens. Use the Pause/Continue button to freeze and resume the threads at any point.

Two output files are generated in the working directory as the simulation runs:
- `simulation_log.txt` — full timestamped event log
- `backing_store_log.txt` — current contents of the simulated disk

## Sample results

The report documents several test runs comparing the two policies:

| Scenario | Page Faults | Page Replacements |
|---|---|---|
| FIFO, single producer/consumer | 12 | 2 |
| LRU, single producer/consumer | 16 | 5 |
| FIFO, 2 producers / 2 consumers | 28 | 18 |
| LRU, 2 producers / 2 consumers | 16 | 6 |

Across both concurrency levels, LRU kept fewer replacements relative to its fault count than FIFO did — consistent with the theory, since FIFO evicts by arrival order alone while LRU tends to keep actively-used pages in memory longer.

## Challenges along the way

- **Race conditions in the shared buffer** — resolved by building the semaphore logic explicitly rather than relying on a black-box synchronization primitive.
- **Keeping FIFO and LRU state independent inside one manager** — resolved by giving each policy its own tracking structure (`Queue` for FIFO order, `Map` for LRU recency) inside `MemoryManager`.
- **Updating Swing components safely from worker threads** — resolved using `SwingUtilities.invokeLater()` wherever the GUI is touched from a non-UI thread.

## Possible extensions

- Additional replacement algorithms (Optimal, Clock/Second-Chance)
- Adjustable buffer size and frame count mid-simulation
- Real-time throughput/performance metrics
- Support for prioritized, multi-process producer-consumer scenarios

## Team

Wahaj Saidam, Masa Alkharsa, Roaa Hamdan, Raneem Jehad, Nora Alomair
Section 807 — Supervised by Dr. Souad Larabi Marie-Sainte

## References

Full citations are included in the accompanying report (`CS330-REPORT.pdf`), covering sources on the producer-consumer problem, virtual memory, backing store concepts, and Java Swing.
