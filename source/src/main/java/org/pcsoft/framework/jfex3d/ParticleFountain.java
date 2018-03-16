package org.pcsoft.framework.jfex3d;

import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import org.pcsoft.framework.jfex.threading.JfxUiThreadPool;
import org.pcsoft.framework.jfex3d.util.DeltaTask;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public final class ParticleFountain extends Group {
    private static final Random RANDOM = new Random();

    private final ObjectProperty<Duration> emissionTimeSpanMin = new SimpleObjectProperty<>(Duration.ofMillis(10));
    private final ObjectProperty<Duration> emissionTimeSpanMax = new SimpleObjectProperty<>(Duration.ofMillis(20));
    private final LongProperty emissionParticleCountMin = new SimpleLongProperty(1);
    private final LongProperty emissionParticleCountMax = new SimpleLongProperty(5);

    private final List<Particle> particleList = new ArrayList<>();

    public ParticleFountain() {
        JfxUiThreadPool.submit(new ParticleAnimation());
    }

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

    private static final class Particle {
        private long lifeTime = 0L;
        private final Shape shape;

        private long energy;
        private Point3D direction;

        private Particle(Color color, double size, double opacity, long energy, Point3D direction) {
            this.shape = new Circle(size);
            this.shape.setFill(color);
            this.shape.setOpacity(opacity);

            this.energy = energy;
            this.direction = direction;
        }
    }

    private final class ParticleAnimation extends DeltaTask {
        private Duration nextEmission = calculateNextDuration();
        private long currentEmisionNanoDelta = 0L;

        private Duration calculateNextDuration() {
            return Duration.ofNanos(RANDOM.nextLong() %
                    (emissionTimeSpanMax.get().getNano() - emissionTimeSpanMin.get().getNano()) + emissionTimeSpanMin.get().getNano());
        }

        @Override
        protected void interpolate(long nanoDelta) {
            Platform.runLater(() -> {
                handleEmission(nanoDelta);
                handleMoving(nanoDelta);
                handleRemove();
            });
        }

        private void handleRemove() {
            particleList.removeAll(
                    particleList.stream()
                            .filter(particle -> particle.lifeTime >= 10000d)
                            .collect(Collectors.toList())
            );
        }

        private void handleMoving(long nanoDelta) {
            for (final Particle particle : particleList) {
                particle.lifeTime += nanoDelta;
                particle.shape.setTranslateX(particle.shape.getTranslateX() + particle.direction.getX() * nanoDelta * 0.01d);
                particle.shape.setTranslateY(particle.shape.getTranslateY() + particle.direction.getY() * nanoDelta * 0.01d);
                particle.shape.setTranslateZ(particle.shape.getTranslateZ() + particle.direction.getZ() * nanoDelta * 0.01d);
            }
        }

        private void handleEmission(long nanoDelta) {
            currentEmisionNanoDelta += nanoDelta;
            if (currentEmisionNanoDelta >= nextEmission.getNano()) {
                runEmission();
                currentEmisionNanoDelta = 0;
                nextEmission = calculateNextDuration();
            }
        }

        private void runEmission() {
            final long count = RANDOM.nextLong() %
                    (emissionParticleCountMax.get() - emissionParticleCountMin.get()) + emissionParticleCountMin.get();

            for (int i = 0; i < count; i++) {
                final Particle particle = new Particle(Color.WHITE, 1d, 1d, 10,
                        new Point3D(Math.abs(RANDOM.nextDouble()) % 2 - 1, Math.abs(RANDOM.nextDouble()) % 2 - 1, Math.abs(RANDOM.nextDouble()) % 2 - 1));
                particleList.add(particle);
                getChildren().add(particle.shape);
            }
        }
    }
}
