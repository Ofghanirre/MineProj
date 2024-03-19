package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.cga.MinecraftLocationRelated;
import org.bukkit.Location;
import org.bukkit.World;
import org.joml.Vector3d;

public class CGAPoint extends CGAAtom implements MinecraftLocationRelated {
    public CGAPoint(double x, double y, double z) {
        super(CGAAtomType.POINT);
        this.put(CGAAtomKey.e1, x);
        this.put(CGAAtomKey.e2, y);
        this.put(CGAAtomKey.e3, z);
    }

    public CGAPoint(Location location) {
        this(location.getX(), location.getY(), location.getZ());
    }

    public double getX() {
        return this.get(CGAAtomKey.e1);
    }
    public double getY() {
        return this.get(CGAAtomKey.e2);
    }
    public double getZ() {
        return this.get(CGAAtomKey.e3);
    }

    public void setX(double value) {
        this.put(CGAAtomKey.e1, value);
    }

    public void setY(double value) {
        this.put(CGAAtomKey.e2, value);
    }

    public void setZ(double value) {
        this.put(CGAAtomKey.e3, value);
    }


    @Override
    public Location toLocation(World world) {
        return new Location(world, getX(), getY(), getZ());
    }

    public CGAPoint copy() {
        return new CGAPoint(this.getX(), this.getY(), this.getZ());
    }
}
