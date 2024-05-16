package com.cpu.scheduler.simulator.schedulers;

import com.cpu.scheduler.simulator.graph.Cluster;
import com.cpu.scheduler.simulator.processes.Process;
import com.cpu.scheduler.simulator.processes.SjfProcess;
import com.cpu.scheduler.simulator.processes.SjfProcess;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class SjfScheduler extends Scheduler {
  private PriorityQueue<SjfProcess> readyProcesses;
  private int contextSwitchingTime;

  public SjfScheduler(int contextSwitchingTime) {
    super();
    this.contextSwitchingTime = contextSwitchingTime;
    this.readyProcesses = new PriorityQueue<>();
  }

  public SjfScheduler(ArrayList<Process> processes, int contextSwitchingTime) {
    super(processes);
    this.contextSwitchingTime = contextSwitchingTime;
    this.readyProcesses = new PriorityQueue<>();
  }

  private void addArrivedProcessesAt(int time) {
    if (arrivalTimeProcesses.containsKey(time)) {
      ArrayList<Process> li = arrivalTimeProcesses.get(time);
      for (Process process : li) {
        SjfProcess sjfProcess = new SjfProcess(process);
        readyProcesses.add(sjfProcess);
      }
    }
  }

  private boolean isAllProcessesFinished(int t, SjfProcess runningProcess) {
    int lastArrivalTime = arrivalTimeProcesses.lastKey();
    return t > lastArrivalTime && readyProcesses.isEmpty() && runningProcess == null;
  }


  @Override
  public ArrayList<Cluster> schedule() {
    ArrayList<Cluster> clusters = new ArrayList<>();
    Cluster cluster = null;
    SjfProcess runningProcess = null;
    int t = -1;
    // Run all processes
    while (!isAllProcessesFinished(t, runningProcess)) {
      t += 1;
      if (runningProcess != null) {
        runningProcess.decreaseBurstTime();
        if (runningProcess.isFinished()) {
          runningProcess = null;
          cluster.setEndTime(t);
          clusters.add(cluster);
        }
      }
      addArrivedProcessesAt(t);
      if (runningProcess == null && !readyProcesses.isEmpty()) {
        runningProcess = readyProcesses.poll(); // runningProcess = shortestProcess
        for (int i = 0; i < contextSwitchingTime; ++i) {
          t += 1;
          addArrivedProcessesAt(t);
        }
        cluster = new Cluster(runningProcess, t);
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
    SjfScheduler scheduler = new SjfScheduler(li, 2);
    var clusters = scheduler.schedule();
    for (Cluster cluster : clusters) {
      System.out.print(cluster.toString());
    }
    System.out.println();
  }
}
