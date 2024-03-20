package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.NotImplementedException;
import org.bukkit.Location;

public class CGADualPoint extends CGAAtom {
    public CGADualPoint(double x, double y, double z) {
        super(ECGADimension.LINE, x, y, z, 0L);
    }

    public CGADualPoint(Location location) {
        super(ECGADimension.LINE, location, 0L);
    }

    @Override
    public <T extends CGAAtom, R extends CGAAtom> R outerProduct(T atom, double infinite) {
        if (atom instanceof CGAPoint point) {
            CGACircle outerResult = computeOuterProduct(point);
            if (infinite == 0L) {
                return (R) new CGACircle(outerResult.x, outerResult.y, outerResult.z);
            }
        }
        throw new NotImplementedException("Behaviour not handled");
    }

    @Override
    public CGADualPoint copy() {
        return new CGADualPoint(this.x, this.y, this.z);
    }

    private CGACircle computeOuterProduct(CGAPoint other) {
        return new CGACircle(
                this.x * other.y - this.y * other.x,
                this.z * other.x - this.x * other.z,
                this.y * other.z - this.z * other.y);
    }
}
