package fr.ofghanirre.mineproj.cga;

import fr.ofghanirre.mineproj.GeoProjectivePlugin;
import fr.ofghanirre.mineproj.cga.atoms.CGAComputedPoint;
import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import fr.ofghanirre.mineproj.cga.operations.EComputeOperation;
import fr.ofghanirre.mineproj.cga.operations.OuterProductOperation;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class CGAPointHolder {
    private final World world;
    private final List<CGAPoint> registeredPoints = new ArrayList<>();
    private final List<CGAPoint> deletedRegisteredPoints = new ArrayList<>();
    private final List<CGAComputedPoint> computedPoints = new ArrayList<>();

    public CGAPointHolder(World world) {
        this.world = world;
        Bukkit.getScheduler().scheduleSyncRepeatingTask(
                GeoProjectivePlugin.getInstance(),
                () -> {
                    if (registeredPoints.isEmpty()) return;
                    for (int i = 0; i < registeredPoints.size() - 1; i++) {
                        CGAPoint point = registeredPoints.get(i);
                        this.world.spawnParticle(Particle.VILLAGER_HAPPY, point.x, point.y, point.z, 0, 0.01, 0.01, 0.01);
                    }
                    CGAPoint lastPoint = registeredPoints.get(registeredPoints.size() - 1);
                    this.world.spawnParticle(Particle.FLAME, lastPoint.x, lastPoint.y, lastPoint.z, 0, 0.01, 0.01, 0.01);
                },
                10L, 5L);
    }


    public int addPoint(CGAPoint point) {
        registeredPoints.add(point);
        for (int i = 0; i < 64; i++) {
            this.world.spawnParticle(Particle.PORTAL, point.x, point.y, point.z, 2);
        }
        return registeredPoints.size();
    }

    public int removePoint(int amount) {
        for (int p = 0; p < amount; p++) {
            if (!registeredPoints.isEmpty()) {
                CGAPoint point = registeredPoints.remove(registeredPoints.size() - 1);
                for (int i = 0; i < 3; i++) {
                    this.world.spawnParticle(Particle.EXPLOSION_LARGE, point.x, point.y, point.z, 2);
                }
                deletedRegisteredPoints.add(point);
            } else {
                return (p == 0) ? -1 : p;
            }
        }
        return registeredPoints.size();
    }

    public int clearPoints() {
        int result = registeredPoints.size();
        this.removePoint(result);
        return result;
    }

    public int restorePoint(int amount) {
        int result = 0;
        for (int p = 0; p < amount; p++) {
            if (deletedRegisteredPoints.isEmpty()) {
                return (p == 0) ? -1 : p;
            }
            result = addPoint(deletedRegisteredPoints.remove(deletedRegisteredPoints.size() - 1));
        }
        return result;
    }

    public int size() {
        return registeredPoints.size();
    }

    public void compute(EComputeOperation eComputeOperation) {
        OuterProductOperation outerProductOperation = new OuterProductOperation(this.registeredPoints);
        outerProductOperation.compute(GeoProjectivePlugin.getInstance().getWorld(), Material.STONE);
    }
}
