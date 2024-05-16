package com.cpu.scheduler.simulator.schedulers;

import com.cpu.scheduler.simulator.graph.Cluster;
import com.cpu.scheduler.simulator.processes.PriorityProcess;
import com.cpu.scheduler.simulator.processes.Process;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class PriorityScheduler extends Scheduler {
  private PriorityQueue<PriorityProcess> readyProcesses;

  private static final int AGE = 30;

  public PriorityScheduler() {
    super();
    readyProcesses = new PriorityQueue<>();
  }

  public PriorityScheduler(ArrayList<Process> processes) {
    super(processes);
    readyProcesses = new PriorityQueue<>();
  }

  private void addArrivedProcessesAt(int time) {
    if (arrivalTimeProcesses.containsKey(time)) {
      ArrayList<Process> li = arrivalTimeProcesses.get(time);
      for (Process process : li) {
        PriorityProcess priorityProcess = new PriorityProcess(process);
        readyProcesses.add(priorityProcess);
      }
    }
  }

  private void increaseOldProcessesPriority(int t) {
    ArrayList<PriorityProcess> temp = new ArrayList<>();
    readyProcesses.forEach((process) -> {
      int diff = t - process.getArrivalTime();
      if (diff % AGE == 0 && diff != 0) {
        process.increasePriority();
        temp.add(process);
      }
    });
    readyProcesses.removeAll(temp);
    readyProcesses.addAll(temp);
  }

  /*
  1- Loop from 0 to the last arrival time
  2- If there is a process that arrives at this time, add it to the ready queue
  3- Increment the priority for old processes
  4- If the
  */
  public ArrayList<Cluster> schedule() {
    int lastArrivalTime = arrivalTimeProcesses.lastKey();
    Process runningProcess = null;
    ArrayList<Cluster> chart = new ArrayList<>();
    Cluster cluster = null;
    for (int t = 0; ; ++t) {
      // Stop if there is no process to run
      if (t > lastArrivalTime && readyProcesses.isEmpty() && runningProcess == null) {
        break;
      }
      // Process the current process
      if (runningProcess != null) {
        runningProcess.decreaseBurstTime();
        if (runningProcess.isFinished()) {
          cluster.setEndTime(t);
          chart.add(cluster);
          runningProcess = null;
        }
      }
      increaseOldProcessesPriority(t);
      addArrivedProcessesAt(t);
      // Run new process if there is no running process
      if (runningProcess == null) {
        runningProcess = readyProcesses.poll();
        cluster = new Cluster(runningProcess, t);
      }
    }
    return chart;
  }

  public static void main(String[] args) {
    ArrayList<Process> processes = new ArrayList<>();
    processes.add(new PriorityProcess("P1", 0, 10, 3));
    processes.add(new PriorityProcess("P2", 0, 1, 1));
    processes.add(new PriorityProcess("P3", 0, 2, 3));
    processes.add(new PriorityProcess("P4", 0, 1, 5));
    processes.add(new PriorityProcess("P5", 6, 5, 2));
    PriorityScheduler scheduler = new PriorityScheduler(processes);
    var li = scheduler.schedule();
    for (Cluster cluster : li) {
      System.out.print(cluster.toString());
    }
  }
}
