package fr.ofghanirre.mineproj.cga.operations;

import fr.ofghanirre.mineproj.cga.atoms.CGAAtom;
import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class OuterProductOperation extends ACGAOperations {

    private final CGAAtom point;
    public OuterProductOperation(List<CGAPoint> list) {
        super(list);
        CGAAtom temp = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            temp = temp.outerProduct(points.get(i));
        }
        this.point = temp;
        System.out.println("POINT REFERENT :" + point);
    }

    @Override
    public void compute(World world, Material material) {
        summon(world, material, cgaPoint -> {
            CGAAtom result = point.outerProduct(cgaPoint);
            System.out.println("Result : " + cgaPoint + " -> " + result);
            return result.isZero();
        });
    }
}
