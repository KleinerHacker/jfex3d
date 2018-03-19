package org.pcsoft.framework.jfex3d.particle.anim;

import javafx.geometry.Point3D;
import org.pcsoft.framework.jfex3d.particle.ParticleFountain;
import org.pcsoft.framework.jfex3d.particle.type.Particle;
import org.pcsoft.framework.jfex3d.util.DeltaScheduler;
import org.pcsoft.framework.jfex3d.util.RandomUtils;

import java.util.stream.Collectors;

public final class ParticleFountainAnimationTimer extends ParticleSystemAnimationTimer<ParticleFountain> {
    private final DeltaScheduler emissionScheduler = new DeltaScheduler(
            () -> RandomUtils.nextLong(particleSystem.getEmissionTimeSpanMin().toNanos(), particleSystem.getEmissionTimeSpanMax().toNanos()));

    public ParticleFountainAnimationTimer(ParticleFountain particleSystem) {
        super(particleSystem);
    }

    @Override
    protected void interpolate(long nanoDelta) {
        //1. Remove death particles
        runDeathParticleRemoving();

        //2. Handle existing particles
        runParticleHandling(nanoDelta);

        //3. New Emission
        if (this.emissionScheduler.isScheduled(nanoDelta) && isEmissionRunning()) {
            runParticleEmission();
        }
    }

    @Override
    public void cleanup() {
        particleList.clear();
    }

    //<editor-fold desc="Handler Methods">
    private void runParticleHandling(long nanoDelta) {
        //Pre-Calculate gravity effect
        final double gravityMovement = particleSystem.getGravityEnergy() * (nanoDelta / 1_000_000_000d);
        final Point3D gravityMovementPoint = new Point3D(
                particleSystem.getGravityDirection().getX() * gravityMovement,
                particleSystem.getGravityDirection().getY() * gravityMovement,
                particleSystem.getGravityDirection().getZ() * gravityMovement
        );

        //Run all
        particleList.forEach(particle -> {
            //Update life time
            particle.setRemainLifeTime(particle.getRemainLifeTime() - (nanoDelta / 1_000_000));
            //Update energy
            if (particle.getEnergy() <= 0 || particle.getEnergy() - particleSystem.getEnergyDamage() <= 0) {
                particle.setEnergy(0);
            } else {
                particle.setEnergy(particle.getEnergy() - particleSystem.getEnergyDamage());
            }

            //Optionals
            if (particleSystem.getParticleDirectionInterpolator() != null) {
                final Point3D newDirection = particleSystem.getParticleDirectionInterpolator().apply(particle.getLifeTimePercentage());
                particle.setDirection(newDirection);
            }
            if (particleSystem.getParticleSizeInterpolator() != null) {
                final Double newSize = particleSystem.getParticleSizeInterpolator().apply(particle.getLifeTimePercentage());
                particle.setSize(newSize);
            }
            if (particleSystem.getParticleOpacityInterpolator() != null) {
                final Double newOpacity = particleSystem.getParticleOpacityInterpolator().apply(particle.getLifeTimePercentage());
                particle.setOpacity(newOpacity);
            }

            //Update position
            final double regularMovement = particle.getEnergy() * (nanoDelta / 1_000_000_000d);
            particle.setPosition(new Point3D(
                    particle.getPosition().getX() + particle.getDirection().getX() * regularMovement + gravityMovementPoint.getX(),
                    particle.getPosition().getY() + particle.getDirection().getY() * regularMovement + gravityMovementPoint.getY(),
                    particle.getPosition().getZ() + particle.getDirection().getZ() * regularMovement + gravityMovementPoint.getZ()
            ));
        });
    }

    private void runDeathParticleRemoving() {
        particleList.removeAll(
                particleList.stream()
                        .filter(particle -> particle.getRemainLifeTime() <= 0L)
                        .collect(Collectors.toList())
        );
    }

    private void runParticleEmission() {
        final long countOfParticles = RandomUtils.nextLong(particleSystem.getEmissionParticleCountMin(), particleSystem.getEmissionParticleCountMax());
        for (int i = 0; i < countOfParticles; i++) {
            final double directionX = RandomUtils.nextDouble(particleSystem.getParticleDirectionMin().getX(), particleSystem.getParticleDirectionMax().getX());
            final double directionY = RandomUtils.nextDouble(particleSystem.getParticleDirectionMin().getY(), particleSystem.getParticleDirectionMax().getY());
            final double directionZ = RandomUtils.nextDouble(particleSystem.getParticleDirectionMin().getZ(), particleSystem.getParticleDirectionMax().getZ());
            final Point3D direction = new Point3D(directionX, directionY, directionZ);

            final double size;
            if (particleSystem.getParticleSizeInterpolator() != null) {
                size = particleSystem.getParticleSizeInterpolator().apply(0d);
            } else {
                size = RandomUtils.nextDouble(particleSystem.getParticleSizeMin(), particleSystem.getParticleSizeMax());
            }

            final double energy;
            if (particleSystem.getParticleEnergyInterpolator() != null) {
                energy = particleSystem.getParticleEnergyInterpolator().apply(direction);
            } else {
                energy = RandomUtils.nextDouble(particleSystem.getParticleEnergyMin(), particleSystem.getParticleEnergyMax());
            }

            final double opacity;
            if (particleSystem.getParticleOpacityInterpolator() != null) {
                opacity = particleSystem.getParticleOpacityInterpolator().apply(0d);
            } else {
                opacity = RandomUtils.nextDouble(particleSystem.getParticleOpacityMin(), particleSystem.getParticleOpacityMax());
            }

            particleList.add(new Particle(
                    RandomUtils.nextLong(particleSystem.getParticleLifeTimeMin(), particleSystem.getParticleLifeTimeMax()),
                    direction, size, energy, opacity
            ));
        }
    }
    //</editor-fold>
}
