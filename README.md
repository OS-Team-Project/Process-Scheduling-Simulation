# Operating Systems Project: Process Scheduling Simulation

## Purpose
This project simulates how an Operating System schedules processes using different CPU scheduling algorithms. It helps understand how processes are managed and executed based on scheduling rules.

## Features
- Reads process data from a file (`processes.txt`).
- Implements two scheduling algorithms: **First-Come, First-Served (FCFS)** and **Shortest Job First (SJF)**.
- Displays a **Gantt Chart** to show execution order.
- Computes **Waiting Time (WT)** and **Turnaround Time (TAT)**.
- Calculates **average WT and TAT** for performance analysis.

## How to Use
### Prerequisites
- Install **GCC/G++** for C/C++ or **JDK** for Java.
- Ensure the input file (`processes.txt`) is formatted correctly.

### Running the Program
1. Clone the repository:
   ```sh
   git clone <repository_url](https://github.com/OS-Team-Project/Process-Scheduling-Simulation.git>
   cd <project_directory>
   ```
2. Compile and run:
   - **For C++:**
     ```sh
     g++ scheduler.cpp -o scheduler
     ./scheduler
     ```
   - **For Java:**
     ```sh
     javac Scheduler.java
     java Scheduler
     ```
3. Provide the necessary inputs or ensure `processes.txt` is present.
4. View the **Gantt Chart** and performance metrics in the output.

## Expected Output
```
| P1 | P2 | P3 | P1 | P4 |
0    2    5    7    12   15
```

## Conclusion
This project provides hands-on experience with CPU scheduling algorithms and helps understand how real-world operating systems manage process execution efficiently.
