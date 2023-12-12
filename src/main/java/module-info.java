module com.cpu.scheduler.simulator {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.cpu.scheduler.simulator to javafx.fxml;
  exports com.cpu.scheduler.simulator;
  exports com.cpu.scheduler.simulator.schedulers;
  opens com.cpu.scheduler.simulator.schedulers to javafx.fxml;
  exports com.cpu.scheduler.simulator.processes;
  opens com.cpu.scheduler.simulator.processes to javafx.fxml;
  exports com.cpu.scheduler.simulator.graph;
  opens com.cpu.scheduler.simulator.graph to javafx.fxml;
}