package fr.ofghanirre.mineproj.event;

import fr.ofghanirre.mineproj.GeoProjectivePlugin;
import fr.ofghanirre.mineproj.cga.atoms.CGAAtom;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class GeoProjEvent implements Listener {
    private static final double TOLERANCE = 1.5; // Tolérance pour arrondir la visée
    @EventHandler
    public void onPlayerLook(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location playerEyeLocation = player.getEyeLocation();

        GeoProjectivePlugin.getInstance().getPointHolder().getRegisteredPoints().forEach(cgaPoint -> {
            // Vérifier si le joueur regarde dans la direction de la position cible
            if (isLookingAtPosition(playerEyeLocation, cgaPoint.toLocation(event.getPlayer().getWorld()), TOLERANCE)) {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacy(getCgaAtomRepresentation(cgaPoint)));
                // Faire quelque chose quand le joueur regarde vers la position cible
            }
        });

    }

    private String getCgaAtomRepresentation(CGAAtom atom) {
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.GRAY).append("Type: ").append(ChatColor.AQUA).append(atom.getType()).append(ChatColor.GRAY).append(" - ");
        for (CGAAtom.CGAAtomKey value : CGAAtom.CGAAtomKey.values()) {
            double elem = atom.get(value);
            if (elem != 0) {
                sb.append(ChatColor.DARK_GREEN).append(value).append(ChatColor.GRAY)
                        .append(":")
                        .append(ChatColor.GREEN).append(String.format("%.2f",elem))
                        .append(ChatColor.GRAY).append(", ");
            }
        }
        return sb.toString();
    }
    private boolean isLookingAtPosition(Location eyeLocation, Location target, double tolerance) {
        Vector direction = target.toVector().subtract(eyeLocation.toVector()).normalize();
        Vector playerDirection = eyeLocation.getDirection();
        double angle = Math.acos(direction.dot(playerDirection));
        angle = Math.toDegrees(angle);
        return angle <= tolerance;
    }
}
