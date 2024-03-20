package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.NotImplementedException;
import org.bukkit.Location;

public class CGACircle extends CGAAtom {
    public CGACircle(double x, double y, double z) {
        super(ECGADimension.PLAN, x, y, z, 1L);
    }

    public CGACircle(Location location) {
        super(ECGADimension.PLAN, location, 1L);
    }

    @Override
    public <T extends CGAAtom, R extends CGAAtom> R outerProduct(T atom, double infinite) {
        if (atom instanceof CGAPoint point) {
            CGAPoint cgaPoint = computeOuterProduct(point);
            return (R) new CGACircle(cgaPoint.x, cgaPoint.y, cgaPoint.z);
        }
        throw new NotImplementedException("Behaviour not handled");
    }

    @Override
    public CGACircle copy() {
        return new CGACircle(this.x, this.y, this.z);
    }

    private CGAPoint computeOuterProduct(CGAAtom other) {
        return new CGAPoint(
                this.x * other.y - this.y * other.x,
                this.z * other.x - this.x * other.z,
                this.y * other.z - this.z * other.y);
    }
}
