package org.pcsoft.framework.jfex3d.particle.anim;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import org.pcsoft.framework.jfex3d.particle.ParticleSystem;
import org.pcsoft.framework.jfex3d.particle.type.Particle;
import org.pcsoft.framework.jfex3d.util.DeltaAnimationTimer;

import java.util.function.Consumer;

public abstract class ParticleSystemAnimationTimer<T extends ParticleSystem> extends DeltaAnimationTimer {
    protected final T particleSystem;
    protected final ObservableList<Particle> particleList = FXCollections.observableArrayList();
    private boolean isEmissionRunning;

    private Consumer<Particle> onAddParticle, onRemoveParticle;

    public ParticleSystemAnimationTimer(T particleSystem) {
        this.particleSystem = particleSystem;
        this.particleList.addListener((ListChangeListener<Particle>) c -> {
            while (c.next()) {
                if (c.wasAdded()) {
                    for (final Particle particle : c.getAddedSubList()) {
                        fireOnAddParticle(particle);
                    }
                }
                if (c.wasRemoved()) {
                    for (final Particle particle : c.getRemoved()) {
                        fireOnRemoveParticle(particle);
                    }
                }
            }
        });
    }

    public T getParticleSystem() {
        return particleSystem;
    }

    //<editor-fold desc="Particle Listener">
    public Consumer<Particle> getOnAddParticle() {
        return onAddParticle;
    }

    public void setOnAddParticle(Consumer<Particle> onAddParticle) {
        this.onAddParticle = onAddParticle;
    }

    public Consumer<Particle> getOnRemoveParticle() {
        return onRemoveParticle;
    }

    public void setOnRemoveParticle(Consumer<Particle> onRemoveParticle) {
        this.onRemoveParticle = onRemoveParticle;
    }
    //</editor-fold>

    /**
     * Cleanup the particle system. Remove all particles and reset system.
     */
    public abstract void cleanup();

    public void startEmission() {
        isEmissionRunning = true;
    }

    public void stopEmission() {
        isEmissionRunning = false;
    }

    public boolean isEmissionRunning() {
        return isEmissionRunning;
    }

    //<editor-fold desc="Fire Event Methods">
    protected final void fireOnAddParticle(Particle particle) {
        if (onAddParticle != null) {
            onAddParticle.accept(particle);
        }
    }

    protected final void fireOnRemoveParticle(Particle particle) {
        if (onRemoveParticle != null) {
            onRemoveParticle.accept(particle);
        }
    }
    //</editor-fold>
}
