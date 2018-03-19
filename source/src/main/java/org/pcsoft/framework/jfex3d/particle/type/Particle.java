package org.pcsoft.framework.jfex3d.particle.type;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.*;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.UUID;

public final class Particle {
    private final UUID uuid = UUID.randomUUID();

    private final LongProperty remainLifeTime = new SimpleLongProperty();
    private final ReadOnlyLongProperty lifeTime;
    private final ObjectProperty<Point3D> direction = new SimpleObjectProperty<>();
    private final ObjectProperty<Color> color = new SimpleObjectProperty<>();
    private final DoubleProperty opacity = new SimpleDoubleProperty(1d);
    private final DoubleProperty size = new SimpleDoubleProperty();
    private final DoubleProperty energy = new SimpleDoubleProperty();

    private final ObjectProperty<Point3D> position = new SimpleObjectProperty<>(new Point3D(0, 0, 0));

    private final DoubleBinding lifeTimePercentage;

    public Particle(long remainLifeTime, Point3D direction, double size, double energy, double opacity) {
        this(remainLifeTime, direction, size, energy, opacity, Color.WHITE);
    }

    public Particle(long lifeTime, Point3D direction, double size, double energy, double opacity, Color color) {
        this.remainLifeTime.set(lifeTime);
        this.lifeTime = new ReadOnlyLongWrapper(lifeTime).getReadOnlyProperty();
        this.direction.set(direction);
        this.color.set(color);
        this.size.set(size);
        this.energy.set(energy);
        this.opacity.set(opacity);

        this.lifeTimePercentage = Bindings.createDoubleBinding(
                () -> Math.min(1d, Math.max(0d, 1d - ((double) this.remainLifeTime.get() / (double) this.lifeTime.get()))),
                this.lifeTime, this.remainLifeTime
        );
    }

    public long getRemainLifeTime() {
        return remainLifeTime.get();
    }

    public LongProperty remainLifeTimeProperty() {
        return remainLifeTime;
    }

    public void setRemainLifeTime(long remainLifeTime) {
        this.remainLifeTime.set(remainLifeTime);
    }

    public long getLifeTime() {
        return lifeTime.get();
    }

    public ReadOnlyLongProperty lifeTimeProperty() {
        return lifeTime;
    }

    public double getLifeTimePercentage() {
        return lifeTimePercentage.get();
    }

    public DoubleBinding lifeTimePercentageProperty() {
        return lifeTimePercentage;
    }

    public Point3D getDirection() {
        return direction.get();
    }

    public ObjectProperty<Point3D> directionProperty() {
        return direction;
    }

    public void setDirection(Point3D direction) {
        this.direction.set(direction);
    }

    public Color getColor() {
        return color.get();
    }

    public ObjectProperty<Color> colorProperty() {
        return color;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public double getOpacity() {
        return opacity.get();
    }

    public DoubleProperty opacityProperty() {
        return opacity;
    }

    public void setOpacity(double opacity) {
        this.opacity.set(opacity);
    }

    public Point3D getPosition() {
        return position.get();
    }

    public ObjectProperty<Point3D> positionProperty() {
        return position;
    }

    public void setPosition(Point3D position) {
        this.position.set(position);
    }

    public double getSize() {
        return size.get();
    }

    public DoubleProperty sizeProperty() {
        return size;
    }

    public void setSize(double size) {
        this.size.set(size);
    }

    public double getEnergy() {
        return energy.get();
    }

    public DoubleProperty energyProperty() {
        return energy;
    }

    public void setEnergy(double energy) {
        this.energy.set(energy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return Objects.equals(uuid, particle.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}