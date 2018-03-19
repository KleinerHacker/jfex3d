package org.pcsoft.framework.jfex3d;

import javafx.application.Application;
import javafx.geometry.Point3D;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import org.pcsoft.framework.jfex3d.particle.ParticleFountain;

public class ParticleFountainTest extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        final Scene scene = new Scene(/*new StackPane(particleFountain)*/ new StackPane(new TestScene()), 800, 600);
        scene.setFill(Color.BLACK);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static final class TestScene extends SubScene {
        public TestScene() {
            super(new Pane(), 800, 600);

            final PerspectiveCamera camera = new PerspectiveCamera(true);
            camera.setTranslateZ(-50);
            camera.setRotationAxis(new Point3D(1, 0, 0));
            camera.setRotate(10);
            this.setCamera(camera);
            this.setRoot(build(camera));
        }
    }

    private static Group build(Camera camera) {
        final Group group = new Group();

        final Canvas canvas = new Canvas(16, 16);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(new RadialGradient(1d, 1d, 0.5d, 0.5d, 1d, true, CycleMethod.REPEAT, new Stop(0d, Color.WHITE), new Stop(1d, Color.TRANSPARENT)));
        gc.fillOval(0, 0, 16, 16);

        final ParticleFountain particleFountain = new ParticleFountain(camera);
        particleFountain.setParticleDirectionMin(new Point3D(-0.5, -1, -0.5));
        particleFountain.setParticleDirectionMax(new Point3D(0.5, -1, 0.5));
        particleFountain.setGravityDirection(new Point3D(0, 1, 0));
        particleFountain.setGravityEnergy(1d);
        particleFountain.setEnergyDamage(0.1d);
        particleFountain.setParticleEnergyMin(5d);
        particleFountain.setParticleEnergyMax(10d);
        particleFountain.setParticleSizeInterpolator(percentage -> Math.abs(Math.sin(percentage * Math.PI * 10d) * (1-percentage)) * 0.5d);
        particleFountain.setParticleImage(canvas.snapshot(new SnapshotParameters() {{setFill(Color.TRANSPARENT);}}, null));
        particleFountain.setEmissionParticleCountMin(10);
        particleFountain.setEmissionParticleCountMax(30);
        group.getChildren().add(particleFountain);

        return group;
    }
}