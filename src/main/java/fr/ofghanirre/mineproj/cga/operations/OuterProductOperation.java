package fr.ofghanirre.mineproj.cga.operations;

import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.List;

public class OuterProductOperation extends ACGAOperations {

    private final CGAPoint point;
    public OuterProductOperation(List<CGAPoint> list) {
        super(list);
        System.out.println(list);
        CGAPoint temp = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            temp = temp.outerProduct(points.get(i));
        }
        point = temp.dual().divide(temp.magnitude());
        System.out.println("POINT REFERENT :" + point);
    }

    @Override
    public void compute(World world, Material material) {
        summon(world, material, cgaPoint -> {
            CGAPoint result = point.outerProduct(cgaPoint);
            System.out.println("Result : " + result);
            System.out.println(result.isZero());
            return result.isZero();
        });
    }
}
