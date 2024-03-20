package fr.ofghanirre.mineproj.cga.operations;

import fr.ofghanirre.mineproj.cga.atoms.CGAAtom;
import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import fr.ofghanirre.mineproj.cga.operations.atoms.BlockTypeRegistration;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class OuterProductOperation extends ACGAOperations {

    public OuterProductOperation(List<CGAPoint> list) {
        super(list);
        System.out.println(list);
        CGAAtom temp = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            System.out.println("Point OP:\n" + temp);
            temp = temp.outerProduct(points.get(i));
        }
        cgaAtom = temp;
        System.out.println("POINT FINAL :\n" + cgaAtom);
    }

    @Override
    public List<BlockTypeRegistration> compute(World world, Material material) {
        return summon(world, material, cgaPoint -> {
            CGAAtom result = cgaAtom.outerProduct(cgaPoint);
            System.out.println(result);
            return result.isZero();
        });
    }
}
