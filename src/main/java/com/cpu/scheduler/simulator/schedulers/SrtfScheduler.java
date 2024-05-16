package com.cpu.scheduler.simulator.schedulers;

import com.cpu.scheduler.simulator.graph.Cluster;
import com.cpu.scheduler.simulator.processes.SrtfProcess;
import com.cpu.scheduler.simulator.processes.Process;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class SrtfScheduler extends Scheduler {
  private PriorityQueue<SrtfProcess> readyProcesses;
  private static final int AGE = 20;

  public SrtfScheduler() {
    super();
    readyProcesses = new PriorityQueue<>();
  }
  public SrtfScheduler(ArrayList<Process> processes) {
    super(processes);
    readyProcesses = new PriorityQueue<>();
  }

  private boolean addArrivedProcessesAt(int time) {
    if (arrivalTimeProcesses.containsKey(time)) {
      ArrayList<Process> li = arrivalTimeProcesses.get(time);
      for (Process process : li) {
        process.setPriority(10);
        SrtfProcess srtfProcess = new SrtfProcess(process);
        readyProcesses.add(srtfProcess);
      }
      return true;
    }
    return false;
  }

  private boolean isAllProcessesFinished(int t, SrtfProcess runningProcess) {
    int lastArrivalTime = arrivalTimeProcesses.lastKey();
    return t > lastArrivalTime && readyProcesses.isEmpty() && runningProcess == null;
  }

  private void increaseOldProcessesPriority(int t) {
    ArrayList<SrtfProcess> temp = new ArrayList<>();
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

  public ArrayList<Cluster> schedule() {
    ArrayList<Cluster> clusters = new ArrayList<>();
    Cluster cluster = null;
    SrtfProcess runningProcess = null;
    int t = -1;
    // Run all processes
    while (!isAllProcessesFinished(t, runningProcess)) {
      t += 1;
      increaseOldProcessesPriority(t);
      if (runningProcess != null) {
        runningProcess.decreaseBurstTime();
        if (runningProcess.isFinished()) {
          runningProcess = null;
          cluster.setEndTime(t);
          clusters.add(cluster);
        }
      }
      boolean isNewProcessesArrived = addArrivedProcessesAt(t);
      if (isNewProcessesArrived || runningProcess == null) {
        if (readyProcesses.isEmpty()) { continue; }
        if (runningProcess == null) {
          runningProcess = readyProcesses.poll(); // runningProcess = shortestProcess
          cluster = new Cluster(runningProcess, t);
        } else if (readyProcesses.peek().compareTo(runningProcess) < 0) {
          SrtfProcess shortestProcess = readyProcesses.poll();
          readyProcesses.add(runningProcess);
          runningProcess = shortestProcess;
          // Cluster part
          cluster.setEndTime(t);
          clusters.add(cluster);
          cluster = new Cluster(runningProcess, t);
        }
      }
    }
    return clusters;
  }

  public static void main(String[] args) {
    ArrayList<Process> li = new ArrayList<>();
    Process p1 = new Process("P1", 0, 7, 5);
    Process p2 = new Process("P2", 2, 4, 5);
    Process p3 = new Process("P3", 4, 1, 5);
    Process p4 = new Process("P4", 5, 4, 5);
    li.add(p1); li.add(p2); li.add(p3); li.add(p4);
    SrtfScheduler scheduler = new SrtfScheduler(li);
    var clusters = scheduler.schedule();
    for (Cluster cluster : clusters) {
      System.out.print(cluster.toString());
    }
    System.out.println();
  }
}