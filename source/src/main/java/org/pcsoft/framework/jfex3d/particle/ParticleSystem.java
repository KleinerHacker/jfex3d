package org.pcsoft.framework.jfex3d.particle;

import javafx.beans.property.*;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.image.Image;

import java.time.Duration;
import java.util.function.Function;

public abstract class ParticleSystem extends Group {
    private final ObjectProperty<Duration> emissionTimeSpanMin = new SimpleObjectProperty<>(Duration.ofMillis(10));
    private final ObjectProperty<Duration> emissionTimeSpanMax = new SimpleObjectProperty<>(Duration.ofMillis(20));
    private final LongProperty emissionParticleCountMin = new SimpleLongProperty(1);
    private final LongProperty emissionParticleCountMax = new SimpleLongProperty(5);

    private final LongProperty particleLifeTimeMin = new SimpleLongProperty(1000L);
    private final LongProperty particleLifeTimeMax = new SimpleLongProperty(3000L);
    private final DoubleProperty particleSizeMin = new SimpleDoubleProperty(0.1d);
    private final DoubleProperty particleSizeMax = new SimpleDoubleProperty(0.3d);
    private final ObjectProperty<Function<Double, Double>> particleSizeInterpolator = new SimpleObjectProperty<>();
    private final DoubleProperty particleEnergyMin = new SimpleDoubleProperty(10d);
    private final DoubleProperty particleEnergyMax = new SimpleDoubleProperty(30d);
    private final ObjectProperty<Function<Point3D, Double>> particleEnergyInterpolator = new SimpleObjectProperty<>();
    private final ObjectProperty<Point3D> particleDirectionMin = new SimpleObjectProperty<>(new Point3D(-1, -1, -1));
    private final ObjectProperty<Point3D> particleDirectionMax = new SimpleObjectProperty<>(new Point3D(1, 1, 1));
    private final ObjectProperty<Function<Double, Point3D>> particleDirectionInterpolator = new SimpleObjectProperty<>();
    private final DoubleProperty particleOpacityMin = new SimpleDoubleProperty(0.3d);
    private final DoubleProperty particleOpacityMax = new SimpleDoubleProperty(0.8d);
    private final ObjectProperty<Function<Double, Double>> particleOpacityInterpolator = new SimpleObjectProperty<>();
    //Design
    private final ObjectProperty<Image> particleImage = new SimpleObjectProperty<>();

    public Duration getEmissionTimeSpanMin() {
        return emissionTimeSpanMin.get();
    }

    public ObjectProperty<Duration> emissionTimeSpanMinProperty() {
        return emissionTimeSpanMin;
    }

    public void setEmissionTimeSpanMin(Duration emissionTimeSpanMin) {
        this.emissionTimeSpanMin.set(emissionTimeSpanMin);
    }

    public Duration getEmissionTimeSpanMax() {
        return emissionTimeSpanMax.get();
    }

    public ObjectProperty<Duration> emissionTimeSpanMaxProperty() {
        return emissionTimeSpanMax;
    }

    public void setEmissionTimeSpanMax(Duration emissionTimeSpanMax) {
        this.emissionTimeSpanMax.set(emissionTimeSpanMax);
    }

    public long getEmissionParticleCountMin() {
        return emissionParticleCountMin.get();
    }

    public LongProperty emissionParticleCountMinProperty() {
        return emissionParticleCountMin;
    }

    public void setEmissionParticleCountMin(long emissionParticleCountMin) {
        this.emissionParticleCountMin.set(emissionParticleCountMin);
    }

    public long getEmissionParticleCountMax() {
        return emissionParticleCountMax.get();
    }

    public LongProperty emissionParticleCountMaxProperty() {
        return emissionParticleCountMax;
    }

    public void setEmissionParticleCountMax(long emissionParticleCountMax) {
        this.emissionParticleCountMax.set(emissionParticleCountMax);
    }

    public double getParticleSizeMin() {
        return particleSizeMin.get();
    }

    public DoubleProperty particleSizeMinProperty() {
        return particleSizeMin;
    }

    public void setParticleSizeMin(double particleSizeMin) {
        this.particleSizeMin.set(particleSizeMin);
    }

    public double getParticleSizeMax() {
        return particleSizeMax.get();
    }

    public DoubleProperty particleSizeMaxProperty() {
        return particleSizeMax;
    }

    public void setParticleSizeMax(double particleSizeMax) {
        this.particleSizeMax.set(particleSizeMax);
    }

    public Function<Double, Double> getParticleSizeInterpolator() {
        return particleSizeInterpolator.get();
    }

    /**
     * Interpolator for life time particle resizing. The in parameter gives the percentage of life time
     * @return
     */
    public ObjectProperty<Function<Double, Double>> particleSizeInterpolatorProperty() {
        return particleSizeInterpolator;
    }

    public void setParticleSizeInterpolator(Function<Double, Double> particleSizeInterpolator) {
        this.particleSizeInterpolator.set(particleSizeInterpolator);
    }

    public long getParticleLifeTimeMin() {
        return particleLifeTimeMin.get();
    }

    public LongProperty particleLifeTimeMinProperty() {
        return particleLifeTimeMin;
    }

    public void setParticleLifeTimeMin(long particleLifeTimeMin) {
        this.particleLifeTimeMin.set(particleLifeTimeMin);
    }

    public long getParticleLifeTimeMax() {
        return particleLifeTimeMax.get();
    }

    public LongProperty particleLifeTimeMaxProperty() {
        return particleLifeTimeMax;
    }

    public void setParticleLifeTimeMax(long particleLifeTimeMax) {
        this.particleLifeTimeMax.set(particleLifeTimeMax);
    }

    public double getParticleEnergyMin() {
        return particleEnergyMin.get();
    }

    /**
     * Minimum energy for a particle. Energy is movement per unit of particle per second
     * @return
     */
    public DoubleProperty particleEnergyMinProperty() {
        return particleEnergyMin;
    }

    public void setParticleEnergyMin(double particleEnergyMin) {
        this.particleEnergyMin.set(particleEnergyMin);
    }

    public double getParticleEnergyMax() {
        return particleEnergyMax.get();
    }

    /**
     * Maximum energy for a particle. Energy is movement per unit of particle per second
     * @return
     */
    public DoubleProperty particleEnergyMaxProperty() {
        return particleEnergyMax;
    }

    public void setParticleEnergyMax(double particleEnergyMax) {
        this.particleEnergyMax.set(particleEnergyMax);
    }

    public Function<Point3D, Double> getParticleEnergyInterpolator() {
        return particleEnergyInterpolator.get();
    }

    /**
     * An energy interpolator to define the used energy in dependency of given direction (Point3D) to create forms (only called if particle is created)
     * @return
     */
    public ObjectProperty<Function<Point3D, Double>> particleEnergyInterpolatorProperty() {
        return particleEnergyInterpolator;
    }

    public void setParticleEnergyInterpolator(Function<Point3D, Double> particleEnergyInterpolator) {
        this.particleEnergyInterpolator.set(particleEnergyInterpolator);
    }

    public Point3D getParticleDirectionMin() {
        return particleDirectionMin.get();
    }

    /**
     * Minimum direction values for a new particle
     * @return
     */
    public ObjectProperty<Point3D> particleDirectionMinProperty() {
        return particleDirectionMin;
    }

    public void setParticleDirectionMin(Point3D particleDirectionMin) {
        this.particleDirectionMin.set(particleDirectionMin);
    }

    public Point3D getParticleDirectionMax() {
        return particleDirectionMax.get();
    }

    /**
     * Maximum direction values for a new particle
     * @return
     */
    public ObjectProperty<Point3D> particleDirectionMaxProperty() {
        return particleDirectionMax;
    }

    public void setParticleDirectionMax(Point3D particleDirectionMax) {
        this.particleDirectionMax.set(particleDirectionMax);
    }

    public Function<Double, Point3D> getParticleDirectionInterpolator() {
        return particleDirectionInterpolator.get();
    }

    /**
     * Interpolator for a life time particle direction change
     * @return
     */
    public ObjectProperty<Function<Double, Point3D>> particleDirectionInterpolatorProperty() {
        return particleDirectionInterpolator;
    }

    public void setParticleDirectionInterpolator(Function<Double, Point3D> particleDirectionInterpolator) {
        this.particleDirectionInterpolator.set(particleDirectionInterpolator);
    }

    public double getParticleOpacityMin() {
        return particleOpacityMin.get();
    }

    public DoubleProperty particleOpacityMinProperty() {
        return particleOpacityMin;
    }

    public void setParticleOpacityMin(double particleOpacityMin) {
        this.particleOpacityMin.set(particleOpacityMin);
    }

    public double getParticleOpacityMax() {
        return particleOpacityMax.get();
    }

    public DoubleProperty particleOpacityMaxProperty() {
        return particleOpacityMax;
    }

    public void setParticleOpacityMax(double particleOpacityMax) {
        this.particleOpacityMax.set(particleOpacityMax);
    }

    public Function<Double, Double> getParticleOpacityInterpolator() {
        return particleOpacityInterpolator.get();
    }

    /**
     * Interpolator for opacity value of a particle in life time
     * @return
     */
    public ObjectProperty<Function<Double, Double>> particleOpacityInterpolatorProperty() {
        return particleOpacityInterpolator;
    }

    public void setParticleOpacityInterpolator(Function<Double, Double> particleOpacityInterpolator) {
        this.particleOpacityInterpolator.set(particleOpacityInterpolator);
    }

    public Image getParticleImage() {
        return particleImage.get();
    }

    /**
     * Image for the particle
     * @return
     */
    public ObjectProperty<Image> particleImageProperty() {
        return particleImage;
    }

    public void setParticleImage(Image particleImage) {
        this.particleImage.set(particleImage);
    }

    public abstract void startAnimation();
    public abstract void pauseAnimation();
    public abstract void stopAnimation();

    public abstract void startEmission();
    public abstract void stopEmission();
}
