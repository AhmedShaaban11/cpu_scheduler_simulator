package com.cpu.scheduler.simulator.processes;

import java.util.Comparator;

public class SjfProcess extends Process implements Comparable<SjfProcess> {

  public SjfProcess(Process process) {
    super(process);
  }

  @Override
  public int compareTo(SjfProcess p) {
    int compare = Integer.compare(this.burstTime, p.burstTime);
    if (compare == 0) {
      return Integer.compare(this.arrivalTime, p.arrivalTime);
    }
    return compare;
  }
}
