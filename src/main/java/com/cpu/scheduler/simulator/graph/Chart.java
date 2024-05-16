package com.cpu.scheduler.simulator.graph;

import com.cpu.scheduler.simulator.processes.Process;

import java.util.*;

public class Chart {
  private ArrayList<Cluster> clusters;
  private Map<Process, Integer> turnAroundTime;
  private Map<Process, Integer> waitingTime;

  public Chart(ArrayList<Cluster> clusters) {
    setClusters(clusters);
  }

  public void setClusters(ArrayList<Cluster> clusters) {
    this.clusters = clusters;
    this.turnAroundTime = new HashMap<>();
    this.waitingTime = new HashMap<>();
    HashMap<Process, Cluster> processClusterMap = new HashMap<>();
    for (Cluster cluster : clusters) {
      Process process = cluster.getProcess();
      process.setBurstTime(process.getBurstTime() + cluster.getTurnaroundTime()); // recompute burst time
      processClusterMap.putIfAbsent(process, new Cluster(process, process.getArrivalTime()));
      processClusterMap.get(process).setEndTime(cluster.getEndTime());
    }
    for (Cluster processCluster : processClusterMap.values()) {
      Process process = processCluster.getProcess();
      this.turnAroundTime.put(process, processCluster.getTurnaroundTime());
      this.waitingTime.put(process, processCluster.getTurnaroundTime() - process.getBurstTime());
    }
  }

  public ArrayList<Cluster> getClusters() {
    return clusters;
  }

  public int getTurnAroundTime(Process process) {
    return turnAroundTime.get(process);
  }

  public Map<Process, Integer> getTurnAroundTime() {
    return turnAroundTime;
  }

  public double getAvgTurnAroundTime() {
    double sum = 0;
    for (int turnAroundTime : this.turnAroundTime.values()) {
      sum += turnAroundTime;
    }
    return sum / this.turnAroundTime.size();
  }

  public int getWaitingTime(Process process) {
    return waitingTime.get(process);
  }

  public Map<Process, Integer> getWaitingTime() {
    return waitingTime;
  }

  public double getAvgWaitingTime() {
    double sum = 0;
    for (int waitingTime : this.waitingTime.values()) {
      sum += waitingTime;
    }
    return sum / this.waitingTime.size();
  }

  public void print() {
    for (Cluster cluster : clusters) {
      System.out.print(cluster);
    }
    System.out.println();
    System.out.println("Average turnaround time: " + getAvgTurnAroundTime());
    System.out.println("Average waiting time: " + getAvgWaitingTime());
    System.out.println("Turnaround time:");
    for (var entry : turnAroundTime.entrySet()) {
      System.out.println(entry.getKey().getName() + ": " + entry.getValue());
    }
    System.out.println("Waiting time:");
    for (var entry : waitingTime.entrySet()) {
      System.out.println(entry.getKey().getName() + ": " + entry.getValue());
    }
    System.out.println("====================================");
//    System.out.println("Turnaround time:");
//    for (Map.Entry<Process, Integer> entry : turnAroundTime.entrySet()) {
//      System.out.println(entry.getKey().getName() + ": " + entry.getValue());
//    }
//    System.out.println("Waiting time:");
//    for (Map.Entry<Process, Integer> entry : waitingTime.entrySet()) {
//      System.out.println(entry.getKey().getName() + ": " + entry.getValue());
//    }
//    System.out.println("Average turnaround time: " + getAvgTurnAroundTime());
//    System.out.println("Average waiting time: " + getAvgWaitingTime());
  }

}
