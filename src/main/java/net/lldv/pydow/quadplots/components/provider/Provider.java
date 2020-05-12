package net.lldv.pydow.quadplots.components.provider;

import cn.nukkit.level.Level;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;

import java.util.List;

public class Provider {

    public void init() {

    }

    public PlotCallback getPlot(Level level, int x, int z) {
        return null;
    }

    public void claimPlot(Plot plot, Player player) {
    }

    public void resetPlot(Plot plot) {

    }

    public List<Plot> getPlotsOfPlayer(String name) {
        return null;
    }

    public PlotCallback findFreePlot(Level level, int amplifierX, int amplifierZ) {
        return null;
    }

    public void addHelper(Plot plot, String helper) {}

    public void removeHelper(Plot plot, String helper) {}

    public void addMember(Plot plot, String member) {}

    public void removeMember(Plot plot, String member) {}

    public void denyPlayer(Plot plot, String player) {}

    public void undenyPlayer(Plot plot, String player) {}

}
