package com.cpu.scheduler.simulator;

import com.cpu.scheduler.simulator.graph.Chart;
import com.cpu.scheduler.simulator.graph.Cluster;
import com.cpu.scheduler.simulator.processes.Process;
import com.cpu.scheduler.simulator.schedulers.Scheduler;
import com.cpu.scheduler.simulator.schedulers.SchedulerFactory;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
  private static Scanner in = new Scanner(System.in);
  private static int IntInput(String msg) {
    System.out.println(msg);
    return in.nextInt();
  }

  private static String StringInput(String msg) {
    System.out.println(msg);
    return in.next();
  }

  public static void main(String[] args) {
//    int n = IntInput("Number of processes: ");
//    int q = IntInput("Round Robin Time quantum: ");
//    int c = IntInput("Context Switching Time: ");
    int n = 2;
    int q = 4;
    int c = 2;
    ArrayList<Process> processes = new ArrayList<>();
    processes.add(new Process("P1", 0, 8, 3));
    processes.add(new Process("P2", 2, 4, 1));
//    for (int i = 0; i < n; ++i) {
//      String name = StringInput("Process name: ");
//      int arrivalTime = IntInput("Arrival time: ");
//      int burstTime = IntInput("Burst time: ");
//      int priority = IntInput("Priority: ");
//      processes.add(new Process(name, arrivalTime, burstTime, priority));
//    }
    ArrayList<Scheduler> schedulers = SchedulerFactory.getAllSchedulers(q, c);
    for (Scheduler scheduler : schedulers) {
      System.out.println(scheduler.getClass().getSimpleName());
      scheduler.setProcesses(processes);
      ArrayList<Cluster> clusters = scheduler.schedule();
      Chart chart = new Chart(clusters);
      chart.print();
    }
  }
}

//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//public class Main extends Application {
//  public static void main(String[] args) {
//    launch(args);
//  }
//
//  @Override
//  public void start(Stage primaryStage) throws Exception {
//    System.out.println(Main.class.getResource("view.fxml"));
//    FXMLLoader loader = new FXMLLoader(Main.class.getResource("view.fxml"));
//    Scene scene = null;
//    try {
//      scene = new Scene(loader.load(), 300, 200);
//    } catch (Exception e) {
//      System.out.println(e.getMessage());
//    }
//    primaryStage.setTitle("JavaFX FXML Example");
//    primaryStage.setScene(scene);
//    primaryStage.show();
//  }
//}
