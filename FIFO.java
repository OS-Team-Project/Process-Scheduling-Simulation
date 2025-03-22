// Sunwoo Yeom
// import packages needed
import java.io.*; // for input output
import java.util.*; //arraylist, scanner, and etc

// Input : PID, Arrival_Time, Burst_Time
// variable needed for WT & TAT : Completion_Time, Waiting_Time, Turnarount_Time

// define Process Data
class ProcessData{
    int PID, Arrival_Time, Burst_Time, Completion_Time, Waiting_Time, Turnarount_Time;

    public ProcessData(int PID, int Arrival_Time, int Burst_Time) { //defining or allowing input from text
        this.PID = PID;
        this.Arrival_Time = Arrival_Time;
        this.Burst_Time = Burst_Time;
    }
}

// main class that takes input from text and save, sort in arrayList + calling function that do scheduling
public class FIFO {
    public static void main(String[] args) {
        // define list that will save inputs taken from processes.txt
        List<ProcessData> processDatas = new ArrayList<>();

        // function to to take text as input
        try {
            File file = new File("processes.txt"); // processes.txt is what we going to scan
            Scanner scanner = new Scanner(file); // scan file a.k.a processes.txt

            if (scanner.hasNextLine()) scanner.nextLine(); // skip the header!

            while (scanner.hasNextLine()) { // keep scanning as long as there is next line
                // split the line into data whenever there is one ore more white spaces
                String[] data = scanner.nextLine().split("\\s+"); 
                int PID = Integer.parseInt(data[0]); // convert first data, which is PID, from string to int
                int Arrival_Time = Integer.parseInt(data[1]); // convert second data, which is Arrival_Time, from string to int
                int Burst_Time = Integer.parseInt(data[2]); // convert third data, which is Burst_Time, from string to int

                processDatas.add(new ProcessData(PID, Arrival_Time, Burst_Time)); // now add above data into list processDatas
            }
            scanner.close(); // don't forget to close scanner

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Error reading file: " + e.getMessage()); // make sure to show what kind of error
            return;
        }

        // Run scheduling function with processDatas as input
        fifoScheduling(processDatas);
    }

    // actual algorithm for sorting, avg wt, avg tat
    public static void fifoScheduling(List<ProcessData> processDatas) {
        // FIFO = sort by Arrival Time
        processDatas.sort(Comparator.comparingInt(p->p.Arrival_Time)); // extrac Arrival_Time from processDatas and compare and sort
        
        // deining variables needed to calculate avg wt, avg tat
        int Current_Time = 0;
        int Total_Waiting_Time = 0;
        int Total_Turnaround_Time = 0;

        // print header
        System.out.println("PID Arrival Burst Completion Waiting Turnaround");

        for (ProcessData processData : processDatas) {
            if (Current_Time < processData.Arrival_Time) {
                Current_Time = processData.Arrival_Time; // update current time only when it is less than Arrival_Time (CPU is Idle)
            }
            
            // process start at curren_time and run for burst = total completion time
            processData.Completion_Time = Current_Time + processData.Burst_Time; 
            // total time from arrival to completion
            processData.Turnarount_Time = processData.Completion_Time - processData.Arrival_Time;
            // time for waiting before execution = Turnaround - burst 
            processData.Waiting_Time = processData.Turnarount_Time - processData.Burst_Time;
            
            Total_Waiting_Time += processData.Waiting_Time; // add up all the waiting time
            Total_Turnaround_Time += processData.Turnarount_Time; // add up all the turnaround time

            System.out.printf("%3d %6d %5d %10d %7d %10d\n", //formatting white space in number of char in name
            processData.PID, processData.Arrival_Time, processData.Burst_Time, 
            processData.Completion_Time, processData.Waiting_Time, processData.Turnarount_Time); //print datas in such order

            Current_Time = processData.Completion_Time; //update time after process completed
        }

        // calculate for avg? total / n
            double average_WaitingTime = (double) Total_Waiting_Time / processDatas.size(); 
            double average_TurnaroundTime = (double) Total_Turnaround_Time / processDatas.size();

            System.out.printf("\nAverage Waiting Time: %.2f\n", average_WaitingTime); // print avg time next line with 2 decimal
            System.out.printf("\nAverage Turnaround Time: %.2f\n", average_TurnaroundTime);

            GanttChart(processDatas);
    }

    public static void GanttChart(List<ProcessData> processDatas) { // function to print Gannt CHart
        System.out.println("\nGantt Chart:"); // printing title as Gantt CHart
        for (ProcessData p : processDatas) {
            System.out.print("| P" + p.PID + " "); // separate chart by ID with |
        }
        System.out.println("|"); // print | at the end

        int time = 0;
        System.out.print(time);
        for (ProcessData p : processDatas) {
            time += p.Burst_Time; // calculating
            System.out.printf(" %4d", time); // printing to fit
        }
        System.out.println();
    }
}

