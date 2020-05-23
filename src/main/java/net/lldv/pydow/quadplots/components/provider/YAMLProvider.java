package net.lldv.pydow.quadplots.components.provider;

import cn.nukkit.level.Level;
import cn.nukkit.player.Player;
import cn.nukkit.utils.Config;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class YAMLProvider extends Provider {

    private HashMap<String, Plot> data = new HashMap<>();
    private Config plotDB;

    @SuppressWarnings("unchecked")
    @Override
    public void init() {
        this.plotDB = new Config(QuadPlots.instance.getDataFolder() + "/plots.yml", Config.YAML);

        for (Map.Entry<String, Object> map : plotDB.getAll().entrySet()) {
            String root = (String) map.getKey();
            String[] rootInfo = root.split(";");

            // ToDo: maybe implement level load
            //if(!QuadPlots.instance.getServer().isLevelLoaded(rootInfo[0])) QuadPlots.instance.getServer().loadLevel(rootInfo[0]);

            Level world = QuadPlots.instance.getServer().getLevelByName(rootInfo[0]);
            int x = Integer.parseInt(rootInfo[1]);
            int z = Integer.parseInt(rootInfo[2]);

            HashMap<String, Object> details = (HashMap<String, Object>) map.getValue();

            String owner = (String) details.get("owner");
            List<String> users = (List<String>) details.get("users");

            List<String> members = new ArrayList<>();
            List<String> helpers = new ArrayList<>();
            List<String> denied = new ArrayList<>();

            for (String str : users) {
                String[] user = str.split(";");
                switch (user[0]) {
                    case "MEMBER":
                        members.add(user[1]);
                        break;
                    case "HELPER":
                        helpers.add(user[1]);
                        break;
                    case "DENY":
                        denied.add(user[1]);
                        break;
                    default:
                        QuadPlots.instance.getLogger().info("Unknown type of user: " + user[0] + " on plot " + x + ";" + z + "!");
                }
            }

            Plot plot = new Plot(x, z, world, owner, helpers, members, denied);
            data.put(root, plot);
        }

        System.out.println("Cached " + data.size() + " Plots.");
    }

    @Override
    public void addHelper(Plot plot, String helper) {
        Plot p = data.get(plot.getRoot());
        p.helpers.add(helper);

        List<String> oldList = plotDB.getStringList(plot.getRoot() + ".users");
        oldList.add("HELPER;" + helper);

        plotDB.set(plot.getRoot() + ".users", oldList);
        plotDB.save();
    }

    @Override
    public void removeHelper(Plot plot, String helper) {
        Plot p = data.get(plot.getRoot());
        p.helpers.remove(helper);

        List<String> oldList = plotDB.getStringList(plot.getRoot() + ".users");
        oldList.remove("HELPER;"+ helper);

        plotDB.set(plot.getRoot() + ".users", oldList);
        plotDB.save();
    }

    @Override
    public void addMember(Plot plot, String member) {
        Plot p = data.get(plot.getRoot());
        p.members.add(member);

        List<String> oldList = plotDB.getStringList(plot.getRoot() + ".users");
        oldList.add("MEMBER;" + member);

        plotDB.set(plot.getRoot() + ".users", oldList);
        plotDB.save();
    }

    @Override
    public void removeMember(Plot plot, String member) {
        Plot p = data.get(plot.getRoot());
        p.members.remove(member);

        List<String> oldList = plotDB.getStringList(plot.getRoot() + ".users");
        oldList.remove("MEMBER;" + member);

        plotDB.set(plot.getRoot() + ".users", oldList);
        plotDB.save();
    }

    @Override
    public void denyPlayer(Plot plot, String player) {
        Plot p = data.get(plot.getRoot());
        p.denied.add(player);

        List<String> oldList = plotDB.getStringList(plot.getRoot() + ".users");
        oldList.add("DENY;" + player);

        plotDB.set(plot.getRoot() + ".users", oldList);
        plotDB.save();
    }

    @Override
    public void undenyPlayer(Plot plot, String player) {
        Plot p = data.get(plot.getRoot());
        p.denied.remove(player);

        List<String> oldList = plotDB.getStringList(plot.getRoot() + ".users");
        oldList.remove("DENY;" + player);

        plotDB.set(plot.getRoot() + ".users", oldList);
        plotDB.save();
    }

    @Override
    public PlotCallback getPlot(Level level, int x, int z) {
        String id = makeID(level, x, z);
        if (data.containsKey(id)) {
            return new PlotCallback(data.get(id), CallbackIDs.CLAIMED);
        } else return new PlotCallback(new Plot(x, z, level), CallbackIDs.FREE);
    }

    @Override
    public void claimPlot(Plot plot, Player player) {
        String id = makeID(plot.world, plot.x, plot.z);

        plot.owner = player.getName();
        plot.members = new ArrayList<>();
        plot.helpers = new ArrayList<>();
        plot.denied = new ArrayList<>();

        plotDB.set(plot.getRoot() + ".owner", player.getName());
        plotDB.set(plot.getRoot() + ".users", new ArrayList<String>());
        plotDB.save();
        data.put(id, plot);
    }

    @Override
    public void resetPlot(Plot plot) {
        plotDB.remove(plot.getRoot());
        data.remove(plot.getRoot());
        plotDB.save();
    }

    @Override
    public List<Plot> getPlotsOfPlayer(String name) {
        return data.values()
                .stream()
                .filter(plot -> plot.owner.equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }

    @Override
    public PlotCallback findFreePlot(Level level, int amplifierX, int amplifierZ) {
        if (data.size() == 0) return getPlot(level, 0, 0);

        // ToDo: Derzeit geht es nur wenn das erste Plot bei X: 0 und Z: 0 ist.

        int lastX = 0;
        int lastZ = 0;

        for (Plot plot : data.values()) {

            int x = plot.x - lastX;
            int y = plot.z - lastZ;
            int diff = Math.abs(x * y);

            if (diff < 4) {
                lastX = plot.x;
                lastZ = plot.z;
                // - |
                PlotCallback cb = getPlot(level, plot.x + 1, plot.z);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x + 1, plot.z, level), CallbackIDs.FREE);
                cb = getPlot(level, plot.x, plot.z + 1);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x, plot.z + 1, level), CallbackIDs.FREE);

                cb = getPlot(level, plot.x - 1, plot.z);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x - 1, plot.z, level), CallbackIDs.FREE);
                cb = getPlot(level, plot.x, plot.z - 1);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x, plot.z - 1, level), CallbackIDs.FREE);

                // / \
                cb = getPlot(level, plot.x + 1 + amplifierX, plot.z - 1 + amplifierZ);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x + 1 + amplifierX, plot.z - 1 + amplifierZ, level), CallbackIDs.FREE);
                cb = getPlot(level, plot.x - 1 + amplifierX, plot.z + 1 + amplifierZ);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x - 1 + amplifierX, plot.z + 1 + amplifierZ, level), CallbackIDs.FREE);

                cb = getPlot(level, plot.x - 1 + amplifierX, plot.z - 1 + amplifierZ);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x - 1 + amplifierX, plot.z - 1 + amplifierZ, level), CallbackIDs.FREE);
                cb = getPlot(level, plot.x + 1 + amplifierX, plot.z + 1 + amplifierZ);
                if (cb.code == CallbackIDs.FREE)
                    return new PlotCallback(new Plot(plot.x + 1 + amplifierX, plot.z + 1 + amplifierZ, level), CallbackIDs.FREE);
            }
        }

        amplifierX += 1;
        amplifierZ -= 1;

        return findFreePlot(level, amplifierX, amplifierZ);
    }

    public String makeID(Level level, int x, int z) {
        return level.getName() + ";" +x + ";" + z;
    }
}
