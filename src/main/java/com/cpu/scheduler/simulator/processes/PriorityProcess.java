package com.cpu.scheduler.simulator.processes;

import javafx.scene.paint.Color;

public class PriorityProcess extends Process implements Comparable<PriorityProcess> {

  public PriorityProcess(String name, int arrivalTime, int burstTime, int priority) {
    super(name, arrivalTime, burstTime, priority);
  }

  public PriorityProcess(String name, int arrivalTime, int burstTime, int priority, Color color) {
    super(name, arrivalTime, burstTime, priority, color);
  }

  public PriorityProcess(Process process) {
    super(process.getName(), process.getArrivalTime(), process.getBurstTime(), process.getPriority(), process.getColor());
  }

  @Override
  public int compareTo(PriorityProcess p) {
    int compare = Integer.compare(this.priority, p.priority);
    if (compare == 0) {
      return Integer.compare(this.arrivalTime, p.arrivalTime);
    }
    return compare;
  }

}
