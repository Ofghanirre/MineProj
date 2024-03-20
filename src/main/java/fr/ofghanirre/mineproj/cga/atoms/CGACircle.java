package fr.ofghanirre.mineproj.cga.atoms;

import org.bukkit.Location;
import org.joml.Vector3d;

public class CGACircle extends CGAAtom {
    public CGACircle(double x, double y, double z, double radius) {
        super(CGAAtomType.CIRCLE);
        this.set(CGAAtomKey.e1, x);
        this.set(CGAAtomKey.e2, y);
        this.set(CGAAtomKey.e3, z);
        this.set(CGAAtomKey.e123, radius);
    }

    public CGACircle(Location center, double radius) {
        this(center.getX(), center.getY(), center.getZ(), radius);
    }

    public double getRadius() {
        return get(CGAAtomKey.e123);
    }

    public void setRadius(double radius) {
        set(CGAAtomKey.e123, radius);
    }

    public Vector3d getCenter() {
        return new Vector3d(get(CGAAtomKey.e1), get(CGAAtomKey.e2), get(CGAAtomKey.e3));
    }

    public void setCenter(double x, double y, double z) {
        this.set(CGAAtomKey.e1, x);
        this.set(CGAAtomKey.e2, y);
        this.set(CGAAtomKey.e3, z);
    }

    public void setCenterX(double x) {
        this.set(CGAAtomKey.e1, x);
    }

    public void setCenterY(double y) {
        this.set(CGAAtomKey.e2, y);
    }

    public void setCenterZ(double z) {
        this.set(CGAAtomKey.e3, z);
    }
}
