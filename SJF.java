import java.io.*;
import java.util.*;

class ProcessData {
    int PID, Arrival_Time, Burst_Time, Completion_Time, Waiting_Time, Turnaround_Time;
    boolean completed = false;

    public ProcessData(int PID, int Arrival_Time, int Burst_Time) {
        this.PID = PID;
        this.Arrival_Time = Arrival_Time;
        this.Burst_Time = Burst_Time;
    }
}

public class SJF {
    public static void main(String[] args) {
        List<ProcessData> processDatas = new ArrayList<>();

        try {
            File file = new File("processes.txt");
            Scanner scanner = new Scanner(file);

            if (scanner.hasNextLine()) scanner.nextLine();

            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split("\\s+");
                int PID = Integer.parseInt(data[0]);
                int Arrival_Time = Integer.parseInt(data[1]);
                int Burst_Time = Integer.parseInt(data[2]);

                processDatas.add(new ProcessData(PID, Arrival_Time, Burst_Time));
            }
            scanner.close();

        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return;
        }

        sjfScheduling(processDatas);
    }

    public static void sjfScheduling(List<ProcessData> processDatas) {
        int currentTime = 0, totalWT = 0, totalTAT = 0, completedProcesses = 0;
        int n = processDatas.size();

        System.out.println("\nGantt Chart:");
        System.out.print("|");

        while (completedProcesses < n) {
            ProcessData shortestJob = null;
            int shortestBurst = Integer.MAX_VALUE;

            for (ProcessData pd : processDatas) {
                if (!pd.completed && pd.Arrival_Time <= currentTime && pd.Burst_Time < shortestBurst) {
                    shortestBurst = pd.Burst_Time;
                    shortestJob = pd;
                }
            }

            if (shortestJob == null) {
                currentTime++;
                continue;
            }

            shortestJob.Completion_Time = currentTime + shortestJob.Burst_Time;
            shortestJob.Turnaround_Time = shortestJob.Completion_Time - shortestJob.Arrival_Time;
            shortestJob.Waiting_Time = shortestJob.Turnaround_Time - shortestJob.Burst_Time;
            shortestJob.completed = true;

            totalWT += shortestJob.Waiting_Time;
            totalTAT += shortestJob.Turnaround_Time;

            System.out.printf(" P%d |", shortestJob.PID);

            currentTime = shortestJob.Completion_Time;
            completedProcesses++;
        }

        // Printing completion times under Gantt chart
        System.out.println();
        currentTime = 0;
        System.out.print(currentTime);
        List<ProcessData> ganttOrder = new ArrayList<>(processDatas);
        ganttOrder.sort(Comparator.comparingInt(p -> p.Completion_Time));

        for (ProcessData pd : ganttOrder) {
            System.out.printf("%5d", pd.Completion_Time);
        }

        // Printing details for each process
        System.out.println("\n\nPID Arrival Burst Completion Waiting Turnaround");
        processDatas.sort(Comparator.comparingInt(p -> p.PID)); // sorting by PID for clearer output
        for (ProcessData pd : processDatas) {
            System.out.printf("%3d %6d %5d %10d %7d %10d\n",
                pd.PID, pd.Arrival_Time, pd.Burst_Time,
                pd.Completion_Time, pd.Waiting_Time, pd.Turnaround_Time);
        }

        System.out.printf("\nAverage Waiting Time: %.2f\n", (double) totalWT / n);
        System.out.printf("Average Turnaround Time: %.2f\n", (double) totalTAT / n);
    }
}
