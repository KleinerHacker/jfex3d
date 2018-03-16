package org.pcsoft.framework.jfex3d;

import javafx.application.Application;
import javafx.scene.AmbientLight;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ParticleFountainTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Scene scene = new Scene(new StackPane(new Group(new ParticleFountain(), new AmbientLight(Color.WHITE))), 800, 600);
        scene.setFill(Color.BLACK);
        final PerspectiveCamera camera = new PerspectiveCamera();
        scene.setCamera(camera);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}