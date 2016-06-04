package com.nenazvan.services.guiApplication;

import com.nenazvan.services.Launcher;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Class run gui application
 */
public class GUIApplication extends Application implements Launcher {

  @Override
  public void start(Stage primaryStage) throws Exception {
    String fxmlFile = "/fxml/main.fxml";
    FXMLLoader loader = new FXMLLoader();
    Parent root = loader.load(getClass().getResourceAsStream(fxmlFile));
    primaryStage.setTitle("MasterService");
    primaryStage.getIcons().add(new Image("/image/icon.jpg"));
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  @Override
  public void launchApplication() {
    launch();
  }
}
