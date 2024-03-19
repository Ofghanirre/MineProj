package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.cga.MinecraftLocationRelated;
import org.bukkit.Location;
import org.bukkit.World;
import org.joml.Vector3d;

public abstract class CGAAtom extends Vector3d implements MinecraftLocationRelated {
    private static final double EPSILON = 1E-6;
    protected final ECGADimension dimension;
    protected final double infinite;

    public CGAAtom(ECGADimension dimension, double x, double y, double z, double infinite) {
        super(x,y,z);
        this.dimension = dimension;
        this.infinite = infinite;
    }

    public CGAAtom(ECGADimension dimension, Location location, double infinite) {
        this(dimension, location.getX(), location.getY(), location.getZ(), infinite);
    }


    public double infinite() {
        return this.infinite;
    }

    public abstract <T extends CGAAtom, R extends CGAAtom> R outerProduct(T atom, double isInfinite);
    public <T extends CGAAtom, R extends CGAAtom> R outerProduct(T atom) {
        return outerProduct(atom, Double.max(this.infinite, atom.infinite()));
    }

    public abstract <T extends CGAAtom> T copy();

    public boolean isZero() {
        return Math.abs(this.x) < EPSILON && Math.abs(this.y) < EPSILON && Math.abs(this.z) < EPSILON;
    }

    @Override
    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    @Override
    public String toString() {
        return "CGAAtom{" +
                "dimension=" + dimension +
                ", infinite=" + infinite +
                ", " + super.toString() +
                '}';
    }
}
