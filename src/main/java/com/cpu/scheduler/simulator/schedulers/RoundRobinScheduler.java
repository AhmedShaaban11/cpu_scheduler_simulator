package com.cpu.scheduler.simulator.schedulers;

import com.cpu.scheduler.simulator.graph.Cluster;
import com.cpu.scheduler.simulator.processes.Process;
import com.cpu.scheduler.simulator.processes.RoundRobinProcess;

import java.util.*;

public class RoundRobinScheduler extends Scheduler {
  private LinkedList<RoundRobinProcess> readyProcesses;
  private LinkedList<RoundRobinProcess> dieProcesses;
  private PriorityQueue<RoundRobinProcess> minAgProcesses;
  private int initialQuantum;

  public RoundRobinScheduler(int quantum) {
    super();
    initialQuantum = quantum;
    readyProcesses = new LinkedList<>();
    dieProcesses = new LinkedList<>();
    minAgProcesses = new PriorityQueue<>();
  }

  public RoundRobinScheduler(ArrayList<Process> processes) {
    super(processes);
    readyProcesses = new LinkedList<>();
    dieProcesses = new LinkedList<>();
    minAgProcesses = new PriorityQueue<>();
  }

  private void addArrivedProcessesAt(int time) {
    if (arrivalTimeProcesses.containsKey(time)) {
      ArrayList<Process> li = arrivalTimeProcesses.get(time);
      for (Process process : li) {
        RoundRobinProcess roundRobinProcess = new RoundRobinProcess(process, initialQuantum);
        readyProcesses.add(roundRobinProcess);
        minAgProcesses.add(roundRobinProcess);
      }
    }
  }

  private boolean isAllProcessesFinished(int t, RoundRobinProcess runningProcess) {
    int lastArrivalTime = arrivalTimeProcesses.lastKey();
    return t > lastArrivalTime && readyProcesses.isEmpty() && runningProcess == null;
  }

  private double getMeanOfQuantum() {
    int sum = 0;
    for (RoundRobinProcess process : readyProcesses) {
      sum += process.getQuantum();
    }
    return sum / (double) readyProcesses.size();
  }

  private void finishRunningProcess(RoundRobinProcess runningProcess) {
    if (runningProcess == null) { return; }
    runningProcess.setQuantum(0);
    readyProcesses.remove(runningProcess);
    minAgProcesses.remove(runningProcess);
    dieProcesses.add(runningProcess);
  }

  public ArrayList<Cluster> schedule() {
    ArrayList<Cluster> chart = new ArrayList<>();
    Cluster cluster = null;
    RoundRobinProcess runningProcess = null;
    int t = -1;
    // Stand on the first process
    while (runningProcess == null) {
      addArrivedProcessesAt(++t);
      runningProcess = minAgProcesses.peek();
      readyProcesses.remove(runningProcess);
    }
    // Run all processes
    while (!isAllProcessesFinished(t, runningProcess)) {
      // Cluster part
      if (cluster != null) {
        cluster.setEndTime(t);
        chart.add(cluster);
      }
      cluster = new Cluster(runningProcess, t);
      int cnt = 0;
      // Non-Preemptive part
      for (; cnt < Math.ceil(runningProcess.getQuantum() / 2.0); ++cnt) {
        t += 1;
        addArrivedProcessesAt(t);
        runningProcess.decreaseBurstTime();
        if (runningProcess.isFinished()) {
          finishRunningProcess(runningProcess);
          runningProcess = readyProcesses.poll();
          cnt = -1;
          break;
        }
      }
      if (cnt == -1) { continue; }
      // Preemptive part
      for (; cnt < runningProcess.getQuantum(); ++cnt) {
        if (minAgProcesses.peek().compareTo(runningProcess) < 0) {
          int newQuntum = (int) Math.ceil(2 * runningProcess.getQuantum() - cnt);
          runningProcess.setQuantum(newQuntum);
          readyProcesses.add(runningProcess);
          runningProcess = minAgProcesses.peek();
          readyProcesses.remove(runningProcess);
          cnt = -1;
          break;
        }
        t += 1;
        addArrivedProcessesAt(t);
        runningProcess.decreaseBurstTime();
        if (runningProcess.isFinished()) {
          finishRunningProcess(runningProcess);
          runningProcess = readyProcesses.poll();
          cnt = -1;
          break;
        }
      }
      if (cnt == -1) { continue; }
      readyProcesses.add(runningProcess);
      int newQuntum = runningProcess.getQuantum() + (int) Math.ceil(0.1 * getMeanOfQuantum());
      runningProcess.setQuantum(newQuntum);
      runningProcess = readyProcesses.poll();
    }
    cluster.setEndTime(t);
    chart.add(cluster);
    return chart;
  }

  public static void main(String[] args) {
    ArrayList<Process> processes = new ArrayList<>();
    processes.add(new Process("P1", 0, 17, 4));
    processes.add(new Process("P2", 3, 6, 9));
    processes.add(new Process("P3", 4, 10, 2));
    processes.add(new Process("P4", 29, 4, 8));
    RoundRobinScheduler scheduler = new RoundRobinScheduler(4);
    scheduler.setProcesses(processes);
    var li = scheduler.schedule();
    for (Cluster cluster : li) {
      System.out.print(cluster.toString());
    }
    System.out.println("done");
  }
}
