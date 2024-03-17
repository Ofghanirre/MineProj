package fr.ofghanirre.mineproj.cga.operations;

import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.List;
import java.util.function.Predicate;

public abstract class ACGAOperations {
    protected final CGAPoint posMin;
    protected final CGAPoint posMax;
    protected final List<CGAPoint> points;

    public ACGAOperations(List<CGAPoint> list) {
        points = list;
        posMin = this.points.get(0).copy();
        posMax = this.points.get(0).copy();
        this.points.forEach(cgaPoint -> {
            posMin.x = Double.min(posMin.x, cgaPoint.x);
            posMin.y = Double.min(posMin.y, cgaPoint.y);
            posMin.z = Double.min(posMin.z, cgaPoint.z);

            posMax.x = Double.max(posMax.x, cgaPoint.x);
            posMax.y = Double.max(posMax.y, cgaPoint.y);
            posMax.z = Double.max(posMax.z, cgaPoint.z);
        });
    }

    protected void summon(World world, Material material, Predicate<CGAPoint> predicate) {
        for (int i = ((int) this.posMin.x); i <= this.posMax.x; i++) {
            for (int j = ((int) this.posMin.y); j <= this.posMax.y; j++) {
                for (int k = ((int) this.posMin.z); k <= this.posMax.z; k++) {
                    CGAPoint p = new CGAPoint(i,j,k);
                    if (true || predicate.test(p)) {
                        System.out.println(p);
                        Block block = world.getBlockAt(p.toLocation(world));
                        block.setType(material);
                    }
                }
            }
        }
    }

    public abstract void compute(World world, Material material);

    public CGAPoint getPosMin() {
        return posMin;
    }

    public CGAPoint getPosMax() {
        return posMax;
    }

    public List<CGAPoint> getPoints() {
        return points;
    }
}
