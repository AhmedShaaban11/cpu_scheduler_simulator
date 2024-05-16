package com.cpu.scheduler.simulator;

import com.cpu.scheduler.simulator.graph.Chart;
import com.cpu.scheduler.simulator.graph.Cluster;
import com.cpu.scheduler.simulator.processes.Process;
import com.cpu.scheduler.simulator.schedulers.Scheduler;
import com.cpu.scheduler.simulator.schedulers.SchedulerFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Controller {

  @FXML
  private TextField numProcessesField;

  @FXML
  private TextField timeQuantumField;

  @FXML
  private TextField contextSwitchingField;

  @FXML
  private VBox processElementsContainer;

  @FXML
  private ComboBox<String> algorithmChoice;

  public void makeFields() {

    // Clear existing elements
    processElementsContainer.getChildren().clear();

    int numProcesses = Integer.parseInt(numProcessesField.getText());

    for (int i = 0; i < numProcesses; i++) {
      HBox processBox = new HBox(10);

      // Color Picker
      ColorPicker colorPicker = new ColorPicker();
      processBox.getChildren().add(colorPicker);

      // Name Field
      TextField nameField = new TextField();
      nameField.setPromptText("Process Name");
      processBox.getChildren().add(nameField);

      // Arrival Time Field
      TextField arrivalTimeField = new TextField();
      arrivalTimeField.setPromptText("Arrival Time");
      processBox.getChildren().add(arrivalTimeField);

      // Burst Time Field
      TextField burstTimeField = new TextField();
      burstTimeField.setPromptText("Burst Time");
      processBox.getChildren().add(burstTimeField);

      // Priority Field
      TextField priorityField = new TextField();
      priorityField.setPromptText("Priority");
      processBox.getChildren().add(priorityField);

      processElementsContainer.getChildren().add(processBox);
    }
  }

  private ArrayList<Process> getProcesses() {
    ArrayList<Process> processes = new ArrayList<>();
    for (int i = 0; i < processElementsContainer.getChildren().size(); i++) {
      HBox processBox = (HBox) processElementsContainer.getChildren().get(i);
      ColorPicker colorPicker = (ColorPicker) processBox.getChildren().get(0);
      TextField nameField = (TextField) processBox.getChildren().get(1);
      TextField arrivalTimeField = (TextField) processBox.getChildren().get(2);
      TextField burstTimeField = (TextField) processBox.getChildren().get(3);
      TextField priorityField = (TextField) processBox.getChildren().get(4);
      processes.add(new Process(
        nameField.getText(),
        Integer.parseInt(arrivalTimeField.getText()),
        Integer.parseInt(burstTimeField.getText()),
        Integer.parseInt(priorityField.getText()),
        colorPicker.getValue()
      ));
      for (Process process : processes) {
        System.out.println(process.toString());
      }
    }
    return processes;
  }

  private void printChart(Chart chart) {
    for (Cluster cluster : chart.getClusters()) {
      System.out.print(cluster.toString());
    }
    System.out.println();
  }

  private void scheduleProcesses(Scheduler scheduler, ArrayList<Process> processes) {
    scheduler.setProcesses(processes);
    var clusters = scheduler.schedule();
    Chart chart = new Chart(clusters);
    printChart(chart);
  }

  public void generate() {
    ArrayList<Process> processes = getProcesses();
    int quantum = Integer.parseInt(timeQuantumField.getText());
    int contextSwitching = Integer.parseInt(contextSwitchingField.getText());
    String algorithm = algorithmChoice.getValue();
    if (algorithm == null) { return; }
    if (algorithm.equalsIgnoreCase("All")) {
      var schedulers = SchedulerFactory.getAllSchedulers(quantum, contextSwitching);
      for (Scheduler scheduler : schedulers) {
        scheduleProcesses(scheduler, processes);
      }
      return;
    }
    Scheduler scheduler = SchedulerFactory.getScheduler(algorithm, quantum, contextSwitching);
    if (scheduler == null) { return; }
    scheduleProcesses(scheduler, processes);
  }
}
