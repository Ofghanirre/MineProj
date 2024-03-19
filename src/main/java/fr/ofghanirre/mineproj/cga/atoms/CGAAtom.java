package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.cga.BehaviourNotHandled;

import java.util.Arrays;

public class CGAAtom {
    public enum CGAAtomKey {
        e0,       // Élément neutre pour l'addition
        e1,       // Vecteur unitaire le long de l'axe x
        e2,       // Vecteur unitaire le long de l'axe y
        e3,       // Vecteur unitaire le long de l'axe z
        e12,      // Bivecteur résultant du produit extérieur de e1 et e2
        e23,      // Bivecteur résultant du produit extérieur de e2 et e3
        e31,      // Bivecteur résultant du produit extérieur de e3 et e1
        e123,     // Trivecteur résultant du produit extérieur de e1, e2 et e3
        e01,      // Bivecteur résultant du produit extérieur de e0 et e1
        e02,      // Bivecteur résultant du produit extérieur de e0 et e2
        e03,      // Bivecteur résultant du produit extérieur de e0 et e3
        e012,     // Trivecteur résultant du produit extérieur de e0, e1 et e2
        e023,     // Trivecteur résultant du produit extérieur de e0, e2 et e3
        e031,     // Trivecteur résultant du produit extérieur de e0, e3 et e1
        e0123,    // Tétravecteur résultant du produit extérieur de e0, e1, e2 et e3
        eInf      // Élément à l'infini (pour la direction ou le point à l'infini)
    }


    protected final double[] _vector = new double[16];
    protected final CGAAtomType type;

    public CGAAtom(double value, CGAAtomType type) {
        Arrays.fill(_vector, value);
        this.type = type;
    }

    public CGAAtom(CGAAtomType type) {
        this(0, type);
    }

    public CGAAtom() {
        this(0, CGAAtomType.POINT);
    }



    public static CGAAtom fromEuclideanPoint(double x, double y, double z) {
        CGAAtom result = new CGAAtom();
        result.put(CGAAtomKey.e1, x);   // Coordonnée x
        result.put(CGAAtomKey.e2, y);   // Coordonnée y
        result.put(CGAAtomKey.e3, z);   // Coordonnée z
        result.put(CGAAtomKey.e0, 1);   // Élément neutre pour l'addition
        result.put(CGAAtomKey.eInf, 0); // Élément à l'infini
        return result;
    }

    public double get(CGAAtomKey key) {
        return this._vector[key.ordinal()];
    }

    public CGAAtom put(CGAAtomKey key, double value) {
        this._vector[key.ordinal()] = value;
        return this;
    }


    protected static CGAAtom pointOuterProductPoint(CGAAtom point1, CGAAtom point2) {
        CGAAtom result = new CGAAtom(CGAAtomType.DUAL_POINT);
        // Calcul de l'outer product
        result.put(CGAAtomKey.e12, point1.get(CGAAtomKey.e1) * point2.get(CGAAtomKey.e2) - point1.get(CGAAtomKey.e2) * point2.get(CGAAtomKey.e1));
        result.put(CGAAtomKey.e23, point1.get(CGAAtomKey.e2) * point2.get(CGAAtomKey.e3) - point1.get(CGAAtomKey.e3) * point2.get(CGAAtomKey.e2));
        result.put(CGAAtomKey.e31, point1.get(CGAAtomKey.e3) * point2.get(CGAAtomKey.e1) - point1.get(CGAAtomKey.e1) * point2.get(CGAAtomKey.e3));
        return result;
    }

    protected static CGAAtom dualPointOuterProductPoint(CGAAtom dualPoint, CGAAtom point) {
        CGAAtom result = new CGAAtom(CGAAtomType.CIRCLE);

        // Calcul de l'outer product
        double e123 = dualPoint.get(CGAAtomKey.e12) * point.get(CGAAtomKey.e3) +
                dualPoint.get(CGAAtomKey.e23) * point.get(CGAAtomKey.e1) +
                dualPoint.get(CGAAtomKey.e31) * point.get(CGAAtomKey.e2);

        // Calcul des coordonnées du centre du cercle
        double e01 = dualPoint.get(CGAAtomKey.e23) * point.get(CGAAtomKey.e2) -
                dualPoint.get(CGAAtomKey.e31) * point.get(CGAAtomKey.e3);
        double e02 = dualPoint.get(CGAAtomKey.e31) * point.get(CGAAtomKey.e1) -
                dualPoint.get(CGAAtomKey.e12) * point.get(CGAAtomKey.e3);
        double e03 = dualPoint.get(CGAAtomKey.e12) * point.get(CGAAtomKey.e2) -
                dualPoint.get(CGAAtomKey.e23) * point.get(CGAAtomKey.e1);

        // Assignation des valeurs au résultat
        result.put(CGAAtomKey.e123, e123);
        result.put(CGAAtomKey.e01, e01);
        result.put(CGAAtomKey.e02, e02);
        result.put(CGAAtomKey.e03, e03);

        // Définir le type du résultat
        return result;
    }


    public CGAAtom circleOuterProductPoint(CGAAtom circle, CGAAtom point) {
        CGAAtom result = new CGAAtom(CGAAtomType.SPHERE);

        // Calcul des coordonnées du centre de la sphère
        double x = (circle.get(CGAAtomKey.e12) * point.get(CGAAtomKey.e3) - (- circle.get(CGAAtomKey.e31)) * point.get(CGAAtomKey.e2)) / circle.get(CGAAtomKey.e123);
        double y = ((-circle.get(CGAAtomKey.e31)) * point.get(CGAAtomKey.e1) - circle.get(CGAAtomKey.e12) * point.get(CGAAtomKey.e3)) / circle.get(CGAAtomKey.e123);
        double z = (circle.get(CGAAtomKey.e12) * point.get(CGAAtomKey.e2) - (- circle.get(CGAAtomKey.e31)) * point.get(CGAAtomKey.e1)) / circle.get(CGAAtomKey.e123);

        // Calcul du rayon de la sphère
        double radiusSquared = (circle.get(CGAAtomKey.e1) * point.get(CGAAtomKey.e1) +
                circle.get(CGAAtomKey.e2) * point.get(CGAAtomKey.e2) +
                circle.get(CGAAtomKey.e3) * point.get(CGAAtomKey.e3) -
                circle.get(CGAAtomKey.e123) * point.get(CGAAtomKey.e123)) / circle.get(CGAAtomKey.e123);

        // Assigner les coordonnées du centre et le rayon à la sphère
        result.put(CGAAtomKey.e1, x);
        result.put(CGAAtomKey.e2, y);
        result.put(CGAAtomKey.e3, z);
        result.put(CGAAtomKey.e123, radiusSquared);

        return result;
    }


    public CGAAtom outerProduct(CGAAtom atom) {
        if (this.getType() == CGAAtomType.POINT && atom.getType() == CGAAtomType.POINT) {
            return pointOuterProductPoint(this, atom);
        } else if (this.getType() == CGAAtomType.DUAL_POINT && atom.getType() == CGAAtomType.POINT) {
            // Dual Point OP Point => Cercle
            return dualPointOuterProductPoint(this, atom);
        } else if (this.getType() == CGAAtomType.CIRCLE && atom.getType() == CGAAtomType.POINT) {
            // CIRCLE OP Point => Cercle
            return circleOuterProductPoint(this, atom);
        }
        throw new BehaviourNotHandled();
    }

    public boolean isZero() {
        for (double value : _vector) {
            if (value != 0) {
                return false;
            }
        }
        return true;
    }

    public CGAAtomType getType() {
        return type;
    }

    public CGAAtom copy() {
        CGAAtom result = new CGAAtom(this.type);
        System.arraycopy(_vector, 0, result._vector, 0, _vector.length);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        // Affichage du type
        sb.append("Type: ").append(type).append("\n");

        // Labels des colonnes
        sb.append(String.format("\t%-8s%-8s%-8s%-8s\n", "e0", "e1", "e2", "e3"));

        // Affichage de la matrice
        for (int i = 0; i < 4; i++) {
            sb.append(String.format("e%d\t", i));
            for (int j = 0; j < 4; j++) {
                sb.append(String.format("%.2f\t", _vector[i * 4 + j]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
