package com.cpu.scheduler.simulator.processes;

import javafx.scene.paint.Color;

import java.util.Comparator;

public class Process {
  private static int idCounter = 0;
  protected int id;
  protected String name;
  protected int arrivalTime;
  protected int burstTime;
  protected int priority;
  protected Color color;

  public Process(String name, int arrivalTime, int burstTime, int priority) {
    this.id = idCounter++;
    this.name = name;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.priority = priority;
    this.color = Color.color(Math.random(), Math.random(), Math.random());
  }

  public Process(String name, int arrivalTime, int burstTime, int priority, Color color) {
    this.id = idCounter++;
    this.name = name;
    this.arrivalTime = arrivalTime;
    this.burstTime = burstTime;
    this.priority = priority;
    this.color = color;
  }

  public Process(Process process) {
    this.id = idCounter++;
    this.name = process.name;
    this.arrivalTime = process.arrivalTime;
    this.burstTime = process.burstTime;
    this.priority = process.priority;
    this.color = process.color;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getArrivalTime() {
    return arrivalTime;
  }

  public void setArrivalTime(int arrivalTime) {
    this.arrivalTime = arrivalTime;
  }

  public int getBurstTime() {
    return burstTime;
  }

  public void setBurstTime(int burstTime) {
    this.burstTime = burstTime;
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    this.priority = priority;
  }

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }

  public void decreaseBurstTime() {
    if (this.burstTime > 0) {
      this.burstTime -= 1;
    }
  }

  public void increasePriority() {
    if (this.priority > 1) {
      this.priority -= 1;
    }
  }

  public boolean isFinished() {
    return this.burstTime == 0;
  }

  public String toString() {
    return this.name + " " + this.arrivalTime + " " + this.burstTime + " " + this.priority + " " + this.color;
  }

}
