package com.cpu.scheduler.simulator.processes;

public class RoundRobinProcess extends Process implements Comparable<RoundRobinProcess> {
  private int quantum;
  private int ag;

  private void setAgFactor() {
    int random = (int) (Math.random() * 20);
    if (random < 10) {
      this.ag = random + this.arrivalTime + this.burstTime;
    } else if (random > 10) {
      this.ag = 10 + this.arrivalTime + this.burstTime;
    } else {
      this.ag = priority + this.arrivalTime + this.burstTime;
    }
  }

  public RoundRobinProcess(String name, int arrivalTime, int burstTime, int priority, int quantum) {
    super(name, arrivalTime, burstTime, priority);
    this.quantum = quantum;
    setAgFactor();
  }

  public RoundRobinProcess(Process process, int quantum) {
    super(process.getName(), process.getArrivalTime(), process.getBurstTime(), process.getPriority(), process.getColor());
    this.quantum = quantum;
    setAgFactor();
  }

  public int getQuantum() {
    return quantum;
  }

  public void setQuantum(int quantum) {
    this.quantum = quantum;
  }

  public int getAg() {
    return ag;
  }

  @Override
  public int compareTo(RoundRobinProcess o) {
    return Integer.compare(this.ag, o.ag);
  }

}
