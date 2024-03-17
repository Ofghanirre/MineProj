package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.cga.operations.EComputeOperation;

import java.util.List;

public record CGAComputedPoint(CGAPoint point, EComputeOperation computeOperation, List<CGAPoint> registeredPoints) {
}
