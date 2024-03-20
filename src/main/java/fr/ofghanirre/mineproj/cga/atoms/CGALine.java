package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.NotImplementedException;
import org.bukkit.Location;

public class CGALine extends CGAAtom {
    public CGALine(double x, double y, double z) {
        super(ECGADimension.LINE, x, y, z, 1L);
    }

    public CGALine(Location location) {
        super(ECGADimension.LINE, location, 1L);
    }

    @Override
    public <T extends CGAAtom, R extends CGAAtom> R outerProduct(T atom, double infinite) {
        if (atom instanceof CGAPoint) {
            return null;
        }
        throw new NotImplementedException("Behaviour not handled");
    }

    @Override
    public CGALine copy() {
        return new CGALine(this.x, this.y, this.z);
    }
}
