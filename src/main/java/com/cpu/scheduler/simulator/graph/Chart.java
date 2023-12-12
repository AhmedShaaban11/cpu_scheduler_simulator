package com.cpu.scheduler.simulator.graph;

import com.cpu.scheduler.simulator.processes.Process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Chart {
  private ArrayList<Cluster> clusters;
  private Map<Process, Integer> turnAroundTime;
  private Map<Process, Integer> waitingTime;

  public Chart() {
    this.clusters = new ArrayList<>();
    this.turnAroundTime = new HashMap<>();
    this.waitingTime = new HashMap<>();
  }

  public void setClusters(ArrayList<Cluster> clusters) {
    this.clusters = clusters;
    this.turnAroundTime = new HashMap<>();
    this.waitingTime = new HashMap<>();
    HashMap<Process, Cluster> processClusterMap = new HashMap<>();
    for (Cluster cluster : clusters) {
      Process process = cluster.getProcess();
      processClusterMap.putIfAbsent(process, cluster);
      processClusterMap.get(process).setEndTime(cluster.getEndTime());
    }
    for (Cluster cluster : processClusterMap.values()) {
      Process process = cluster.getProcess();
      this.turnAroundTime.put(process, cluster.getTurnaroundTime());
      this.waitingTime.put(process, cluster.getTurnaroundTime() - process.getBurstTime());
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

}
