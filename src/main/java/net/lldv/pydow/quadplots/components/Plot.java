package net.lldv.pydow.quadplots.components;

import cn.nukkit.level.Level;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;

import java.util.List;

public class Plot {

    public int x;
    public int z;
    public Level world;
    public String owner;
    public List<String> helpers;
    public List<String> members;
    public List<String> denied;

    public Plot(int x, int z, Level world) {
        this.x = x;
        this.z = z;
        this.world = world;
    }

    public Plot(int x, int z, Level world, String owner, List<String> helpers, List<String> members, List<String> denied) {
        this.x = x;
        this.z = z;
        this.world = world;
        this.owner = owner;
        this.helpers = helpers;
        this.members = members;
        this.denied = denied;
    }

    public boolean isClaimed() {
        return owner != null;
    }

    public boolean hasBuildPermission(String player) {
        if (!isClaimed()) return false;
        if (owner.equalsIgnoreCase(player)) return true;
        if (members.contains(player)) return true;
        Player own = QuadPlots.instance.getServer().getPlayer(owner);
        if (own != null) {
            if (helpers.contains(player) && own.isOnline()) return true;
        }
        return false;
    }

    public boolean isAllowedToEnter(String player) {
        if (owner.equalsIgnoreCase(player)) return true;
        if (denied.contains(player)) return false;
        return true;
    }

    public String getRoot() {
        return world.getName() + ";" + x + ";" + z;
    }

}
