package com.cpu.scheduler.simulator.processes;

public class SrtfProcess extends Process implements Comparable<SrtfProcess>  {

  public SrtfProcess(Process process) {
    super(process);
  }

  @Override
  public int compareTo(SrtfProcess p) {
    if (this.priority == 1 && p.priority != 1) {
      return -1;
    } else if (this.priority != 1 && p.priority == 1) {
      return 1;
    }
    int compare = Integer.compare(this.burstTime, p.burstTime);
    if (compare == 0) {
      return Integer.compare(this.arrivalTime, p.arrivalTime);
    }
    return compare;
  }
}
