package fr.ofghanirre.mineproj.cga.atoms;

import fr.ofghanirre.mineproj.cga.MinecraftLocationRelated;
import org.bukkit.Location;
import org.bukkit.World;
import org.joml.Vector3d;

public class CGAPoint extends Vector3d implements MinecraftLocationRelated {
    public CGAPoint() {
    }

    public CGAPoint(double d) {
        super(d);
    }

    public CGAPoint(double x, double y, double z) {
        super(x, y, z);
    }

    public CGAPoint(Location location) { super(location.getX(), location.getY(), location.getZ()); }
    public CGAPoint outerProduct(Vector3d other) {
        double newX = this.y * other.z - this.z * other.y;
        double newY = this.z * other.x - this.x * other.z;
        double newZ = this.x * other.y - this.y * other.x;

        return new CGAPoint(newX, newY, newZ);
    }

    // Fonction pour vérifier si un vecteur est proche de zéro
    public boolean isZero() {
        int xi = (int) x;
        int yi = (int) y;
        int zi = (int) z;
        System.out.println("Point Int : " + xi + " " + yi + " " + zi);
        return (xi == 0) && (yi == 0) && (zi == 0);
    }

    public CGAPoint dual() {
        // Calcul du dual du vecteur
        double newX = -this.y;
        double newY = this.x;
        double newZ = 0; // Le dual d'un vecteur 3D est un vecteur 2D, donc la composante z est toujours 0
        return new CGAPoint(newX, newY, newZ);
    }

    // Méthode pour la norme du vecteur
    public double magnitude() {
        return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public CGAPoint copy() {
        return new CGAPoint(this.x, this.y, this.z);
    }

    @Override
    public Location toLocation(World world) {
        return new Location(world, x, y, z);
    }

    public CGAPoint divide(double scalar) {
        // Division des composantes du vecteur par le scalaire
        double newX = this.x / scalar;
        double newY = this.y / scalar;
        double newZ = this.z / scalar;
        return new CGAPoint(newX, newY, newZ);
    }
}
