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

  public void increasePriority() {
    // 1 is the highest priority
    if (this.priority > 1) {
      this.priority -= 1;
    }
  }

  @Override
  public int compareTo(PriorityProcess p) {
    return Integer.compare(this.priority, p.priority);
  }

}
