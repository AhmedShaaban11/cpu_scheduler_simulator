module com.cpu.scheduler.simulator {
  requires javafx.controls;
  requires javafx.fxml;


  opens com.cpu.scheduler.simulator to javafx.fxml;
  exports com.cpu.scheduler.simulator;
}