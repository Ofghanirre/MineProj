package fr.ofghanirre.mineproj.cga.operations.atoms;

import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import fr.ofghanirre.mineproj.cga.operations.EComputeOperation;

import java.util.List;

public record CGAComputedPoint(EComputeOperation operation, List<CGAPoint> points, List<BlockTypeRegistration> blockMemory) {
}
