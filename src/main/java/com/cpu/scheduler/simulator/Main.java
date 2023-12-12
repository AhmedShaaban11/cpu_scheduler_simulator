package com.cpu.scheduler.simulator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    System.out.println(Main.class.getResource("view.fxml"));
    FXMLLoader loader = new FXMLLoader(Main.class.getResource("view.fxml"));
    Scene scene = null;
    try {
      scene = new Scene(loader.load(), 300, 200);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
    primaryStage.setTitle("JavaFX FXML Example");
    primaryStage.setScene(scene);
    primaryStage.show();
  }
}
