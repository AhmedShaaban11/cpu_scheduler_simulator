package com.cpu.scheduler.simulator.schedulers;

import com.cpu.scheduler.simulator.graph.Cluster;
import com.cpu.scheduler.simulator.processes.Process;

import java.util.ArrayList;
import java.util.TreeMap;

public abstract class Scheduler {
  protected TreeMap<Integer, ArrayList<Process>> arrivalTimeProcesses; // ArrivalTime -> Processes

  public Scheduler() {
    arrivalTimeProcesses = new TreeMap<>();
  }

  public Scheduler(ArrayList<Process> processes) {
    setProcesses(processes);
  }

  public void setProcesses(ArrayList<Process> processes) {
    arrivalTimeProcesses = new TreeMap<>();
    for (Process process : processes) {
      int arrivalTime = process.getArrivalTime();
      arrivalTimeProcesses.computeIfAbsent(arrivalTime, k -> new ArrayList<>());
      arrivalTimeProcesses.get(arrivalTime).add(process);
    }
  }

  public ArrayList<Process> getProcessesAt(int arrivalTime) {
    return arrivalTimeProcesses.get(arrivalTime);
  }

  public abstract ArrayList<Cluster> schedule();
}
