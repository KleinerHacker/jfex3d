package org.pcsoft.framework.jfex3d.particle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Camera;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import org.pcsoft.framework.jfex3d.particle.anim.ParticleFountainAnimationTimer;
import org.pcsoft.framework.jfex3d.shape.Billboard;
import org.pcsoft.framework.jfex3d.util.RandomUtils;

public final class ParticleFountain extends ParticleSystem {
    private final ParticleFountainAnimationTimer animationTimer;

    private final DoubleProperty energyDamage = new SimpleDoubleProperty(0.1d);

    private final ObjectProperty<Point3D> gravityDirection = new SimpleObjectProperty<>(new Point3D(0, 0, 0));
    private final DoubleProperty gravityEnergy = new SimpleDoubleProperty(0d);

    public ParticleFountain(final Camera camera) {
        animationTimer = new ParticleFountainAnimationTimer(this);
        animationTimer.setOnAddParticle(particle -> {
            final double size = RandomUtils.nextDouble(getParticleSizeMin(), getParticleSizeMax());
            final Billboard shape = new Billboard(size, size);
            shape.setMaterial(new PhongMaterial(Color.WHITE, getParticleImage(), null, null, null));
            shape.rotationAxisProperty().bind(camera.rotationAxisProperty());
            shape.rotateProperty().bind(camera.rotateProperty());
            shape.setUserData(particle);
            shape.translateXProperty().bind(Bindings.createDoubleBinding(() -> particle.getPosition().getX(), particle.positionProperty()));
            shape.translateYProperty().bind(Bindings.createDoubleBinding(() -> particle.getPosition().getY(), particle.positionProperty()));
            shape.translateZProperty().bind(Bindings.createDoubleBinding(() -> particle.getPosition().getZ(), particle.positionProperty()));
            shape.widthProperty().bind(particle.sizeProperty());
            shape.heightProperty().bind(particle.sizeProperty());
            getChildren().add(shape);
        });
        animationTimer.setOnRemoveParticle(particle ->
                getChildren().stream()
                        .filter(item -> item.getUserData().equals(particle)).findFirst()
                        .ifPresent(node -> getChildren().remove(node))
        );
        
        startAnimation();
        startEmission();
    }

    public double getEnergyDamage() {
        return energyDamage.get();
    }

    /**
     * Damage of energy per second
     * @return
     */
    public DoubleProperty energyDamageProperty() {
        return energyDamage;
    }

    public void setEnergyDamage(double energyDamage) {
        this.energyDamage.set(energyDamage);
    }

    public Point3D getGravityDirection() {
        return gravityDirection.get();
    }

    /**
     * Direction of gravity
     * @return
     */
    public ObjectProperty<Point3D> gravityDirectionProperty() {
        return gravityDirection;
    }

    public void setGravityDirection(Point3D gravityDirection) {
        this.gravityDirection.set(gravityDirection);
    }

    public double getGravityEnergy() {
        return gravityEnergy.get();
    }

    /**
     * Energy of gravity per second in units
     * @return
     */
    public DoubleProperty gravityEnergyProperty() {
        return gravityEnergy;
    }

    public void setGravityEnergy(double gravityEnergy) {
        this.gravityEnergy.set(gravityEnergy);
    }

    @Override
    public void startAnimation() {
        animationTimer.start();
    }

    @Override
    public void pauseAnimation() {
        animationTimer.stop();
    }

    @Override
    public void stopAnimation() {
        animationTimer.stop();
        animationTimer.cleanup();
    }

    @Override
    public void startEmission() {
        animationTimer.startEmission();
    }

    @Override
    public void stopEmission() {
        animationTimer.stopEmission();
    }
}
