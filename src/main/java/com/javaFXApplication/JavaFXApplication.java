package com.javaFXApplication;

import com.javaFXApplication.controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**     Main  extends JavaFX Application class
 *        @author Adam Combs
 */
public class JavaFXApplication extends Application {

          private double xOffset = 10;
          private double yOffset = 10;

          public static void main(String[] args) { launch(args); }

          @Override
          public void start(Stage mainStage) throws IOException {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
                    Parent root = loader.load();
                    MainController controller =  loader.getController();
                    controller.setStage(mainStage);

                    root.setOnMousePressed(event -> {
                              xOffset = event.getSceneX();
                              yOffset = event.getSceneY();
                    });

                    root.setOnMouseDragged(event -> {
                              mainStage.setX(event.getScreenX() - xOffset);
                              mainStage.setY(event.getScreenY() - yOffset);
                    });

                    mainStage.initStyle(StageStyle.TRANSPARENT);
                    mainStage.getIcons().add(new Image(getClass().getResource("/images/icon.png").toString()));
                    mainStage.setTitle("Main Stage");
                    mainStage.setResizable(true);

                    Scene mainScene = new Scene(root);
                    mainScene.getStylesheets().add(getClass().getResource("/css/stylez.css").toString());
                    mainStage.setScene(mainScene);

                    mainStage.show();
          }

          @Override
          public void stop() {
                    System.exit(0);
          }

}