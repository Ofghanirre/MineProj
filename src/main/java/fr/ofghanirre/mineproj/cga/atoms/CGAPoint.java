package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.NotImplementedException;
import org.bukkit.Location;
import org.joml.Vector3d;

import java.lang.invoke.WrongMethodTypeException;

public class CGAPoint extends CGAAtom  {

    public CGAPoint(double x, double y, double z) {
        super(ECGADimension.POINT, x, y, z, 0L);
    }

    public CGAPoint(Location location) { super(ECGADimension.POINT, location, 0L); }

    @Override
    public <T extends CGAAtom, R extends CGAAtom> R outerProduct(T atom, double infinite) {
        if (atom instanceof CGAPoint) {
            CGAPoint outerResult = computeOuterProduct(atom);
            if (infinite == 0L) {
                return (R) new CGADualPoint(outerResult.x, outerResult.y, outerResult.z);
            } else {
                return (R) new CGALine(outerResult.x, outerResult.y, outerResult.z);
            }
        }
        throw new NotImplementedException("Behaviour not handled");
    }

    public boolean isWithin(CGAAtom atom) {
        return this.outerProduct(atom).isZero();
    }

    @Override
    public CGAPoint copy() {
        return new CGAPoint(this.x, this.y, this.z);
    }


    private CGAPoint computeOuterProduct(CGAAtom other) {
        return new CGAPoint(
                this.x * other.y - this.y * other.x,
                this.z * other.x - this.x * other.z,
                this.y * other.z - this.z * other.y);
    }
}
