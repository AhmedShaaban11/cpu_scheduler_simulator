package com.cpu.scheduler.simulator.graph;

import com.cpu.scheduler.simulator.processes.Process;

public class Cluster {
  private Process process;
  private int startTime;
  private int endTime;

  public Cluster(Process process, int startTime) {
    this.process = process;
    this.startTime = startTime;
    this.endTime = -1;
  }

  public Process getProcess() {
    return process;
  }

  public void setProcess(Process process) {
    this.process = process;
  }

  public int getStartTime() {
    return startTime;
  }

  public void setStartTime(int startTime) {
    this.startTime = startTime;
  }

  public int getEndTime() {
    return endTime;
  }

  public void setEndTime(int endTime) {
    this.endTime = endTime;
  }

  public int getTurnaroundTime() {
    return this.endTime - this.startTime;
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
    sb.append(process.getName());
    // append the remaining spaces
    for (int i = 0; i < Math.ceil(getTurnaroundTime() / 2.0); i++) {
      sb.append(" ");
    }
    sb.append(endTime);
    sb.append('|');
    return sb.toString();
  }

}
