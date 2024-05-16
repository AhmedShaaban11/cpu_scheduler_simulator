package com.cpu.scheduler.simulator.graph;

import com.cpu.scheduler.simulator.processes.Process;

public class ClusterQ extends Cluster {
  private int startQ;
  private int endQ;

  public ClusterQ(Process p, int startTime, int startQ) {
    super(p, startTime);
    this.startQ = startQ;
    this.endQ = -1;
  }

  public int getStartQ() {
    return startQ;
  }

  public void setStartQ(int startQ) {
    this.startQ = startQ;
  }

  public int getEndQ() {
    return endQ;
  }

  public void setEndQ(int endQ) {
    this.endQ = endQ;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append('|');
    sb.append(startTime);
    // append half turnaround spaces
    for (int i = 0; i < Math.ceil(getTurnaroundTime() / 2.0); i++) {
      sb.append(" ");
    }
    sb.append(startQ).append("->").append(process.getName()).append("->").append(endQ);
    // append the remaining spaces
    for (int i = 0; i < Math.ceil(getTurnaroundTime() / 2.0); i++) {
      sb.append(" ");
    }
    sb.append(endTime);
    sb.append('|');
    return sb.toString();
  }
}
