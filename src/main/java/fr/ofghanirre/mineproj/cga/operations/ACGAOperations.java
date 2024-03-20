package fr.ofghanirre.mineproj.cga.operations;

import fr.ofghanirre.mineproj.cga.atoms.CGAAtom;
import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import fr.ofghanirre.mineproj.cga.operations.atoms.BlockTypeRegistration;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class ACGAOperations {
    protected final CGAPoint posMin;
    protected final CGAPoint posMax;
    protected final List<CGAPoint> points;

    protected CGAAtom cgaAtom;


    public ACGAOperations(List<CGAPoint> list) {
        points = list;
        posMin = this.points.get(0).copy();
        posMax = this.points.get(0).copy();
        this.points.forEach(cgaPoint -> {
            posMin.setX(Double.min(posMin.getX(), cgaPoint.getX()));
            posMin.setY(Double.min(posMin.getY(), cgaPoint.getY()));
            posMin.setZ(Double.min(posMin.getZ(), cgaPoint.getZ()));

            posMax.setX(Double.max(posMax.getX(), cgaPoint.getX()));
            posMax.setY(Double.max(posMax.getY(), cgaPoint.getY()));
            posMax.setZ(Double.max(posMax.getZ(), cgaPoint.getZ()));
        });
    }

    protected List<BlockTypeRegistration> summon(World world, Material material, Predicate<CGAPoint> predicate) {
        List<BlockTypeRegistration> result = new ArrayList<>();
        for (int i = ((int) this.posMin.getX()); i <= this.posMax.getX(); i++) {
            for (int j = ((int) this.posMin.getY()); j <= this.posMax.getY(); j++) {
                for (int k = ((int) this.posMin.getZ()); k <= this.posMax.getZ(); k++) {
                    CGAPoint p = new CGAPoint(i,j,k);
                    if (predicate.test(p)) {
                        Location location = p.toLocation(world);
                        Block block = world.getBlockAt(location);
                        result.add(new BlockTypeRegistration(location, block.getType()));
                        block.setType(material);
                    }
                }
            }
        }
        return result;
    }

    public abstract List<BlockTypeRegistration> compute(World world, Material material);

    public CGAPoint getPosMin() {
        return posMin;
    }

    public CGAPoint getPosMax() {
        return posMax;
    }

    public List<CGAPoint> getPoints() {
        return points;
    }

    public CGAAtom getCgaAtom() {
        return cgaAtom;
    }

    public void setCgaAtom(CGAAtom cgaAtom) {
        this.cgaAtom = cgaAtom;
    }
}
