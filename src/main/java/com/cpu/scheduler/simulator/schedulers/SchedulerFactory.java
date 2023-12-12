package com.cpu.scheduler.simulator.schedulers;

import java.util.ArrayList;

public class SchedulerFactory {
  public static Scheduler getScheduler(String schedulerName, int quantum, int contextSwitchingTime) {
    switch (schedulerName) {
      case "Priority":
        return new PriorityScheduler();
      case "RoundRobin":
        return new RoundRobinScheduler(quantum);
      default:
        return null;
    }
  }

  public static ArrayList<Scheduler> getAllSchedulers(int quantum, int contextSwitchingTime) {
    ArrayList<Scheduler> schedulers = new ArrayList<>();
    schedulers.add(new PriorityScheduler());
    schedulers.add(new RoundRobinScheduler(quantum));
    return schedulers;
  }
}
