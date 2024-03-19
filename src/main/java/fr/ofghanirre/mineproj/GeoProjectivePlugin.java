package fr.ofghanirre.mineproj;

import fr.ofghanirre.mineproj.cga.atoms.CGAAtom;
import fr.ofghanirre.mineproj.cga.atoms.CGAPoint;
import fr.ofghanirre.mineproj.cga.CGAPointHolder;
import fr.ofghanirre.mineproj.commands.GeoProjCommands;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GeoProjectivePlugin extends JavaPlugin {
    private static GeoProjectivePlugin INSTANCE;
    private final List<CGAPoint> registeredPoints = new ArrayList<>();
    private World world;
    public CGAPointHolder getPointHolder() {
        return pointHolder;
    }

    private CGAPointHolder pointHolder;

    public static GeoProjectivePlugin getInstance() { return INSTANCE; }

    public World getWorld() {
        return this.world;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        getLogger().info("onEnable is called!");
        Objects.requireNonNull(this.getCommand("cga")).setExecutor(new GeoProjCommands());
        this.world = this.getServer().getWorlds().get(0);
        this.getServer().setMotd(
                ChatColor.AQUA +"GeoProjective" + ChatColor.GOLD + " Project server Host\n"
              + ChatColor.BLUE + ChatColor.BOLD + "IGM" + ChatColor.GRAY + " - 2023/24  " + ChatColor.GREEN + "Antonin JEAN");

        this.pointHolder = new CGAPointHolder(this.world);
    }

    @Override
    public void onDisable() {
        this.pointHolder.clearPoints();
        getLogger().info("onDisable is called!");
    }
}