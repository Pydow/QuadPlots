package net.lldv.pydow.quadplots;

import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.CommandRegistry;
import cn.nukkit.utils.Identifier;
import net.lldv.pydow.quadplots.commands.PlotCommand;
import net.lldv.pydow.quadplots.commands.SubCommandHandler;
import net.lldv.pydow.quadplots.commands.sub.InfoCommand;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.generator.QuadPlotGen;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

public class QuadPlots extends PluginBase {

    public static QuadPlots instance;
    public static Identifier id = Identifier.fromString("quadplots:default");

    @Override
    public void onLoad() {
        instance = this;
        PlotSettings.load();
        Language.init();
        getServer().getGeneratorRegistry().register(id, new QuadPlotGen(), 0);
        CommandRegistry cr = getServer().getCommandRegistry();
        cr.register(this, "plot", new PlotCommand());
    }

    @Override
    public void onEnable() {
        registerSubCommands();
    }

    public void registerSubCommands() {
        SubCommandHandler.register("info", new InfoCommand());
    }

    public Location getPlotPosition(Plot plot) {
        int plotSize = PlotSettings.plotSize;
        int roadWidth = PlotSettings.roadWidth;
        int totalSize = plotSize + roadWidth;
        int x = totalSize * plot.x;
        int z = totalSize * plot.z;
        Level level = plot.world;
        return Location.from(x, PlotSettings.groundHeight, z, level);
    }

    public PlotCallback getPlotByPosition(Location position) {
        double $x = position.getX();
        double $z = position.getZ();
        int $X;
        int $Z;
        double $difX;
        double $difZ;

        // ToDo: level check!!
        //String levelName = position.getLevel().getName();
        //if (!levelName.equalsIgnoreCase(PlotSettings.world)) return new PlotCallback(null, CallbackIDs.ON_ROAD);


        int plotSize = PlotSettings.plotSize;
        int roadWidth = PlotSettings.roadWidth;
        int totalSize = plotSize + roadWidth;
        if ($x >= 0) {
            $X = (int) Math.floor($x / totalSize);
            $difX = $x % totalSize;
        } else {
            $X = (int) Math.ceil(($x - plotSize + 1) / totalSize);
            $difX = Math.abs(($x - plotSize + 1) % totalSize);
        }
        if ($z >= 0) {
            $Z = (int) Math.floor($z / totalSize);
            $difZ = $z % totalSize;
        } else {
            $Z = (int) Math.ceil(($z - plotSize + 1) / totalSize);
            $difZ = Math.abs(($z - plotSize + 1) % totalSize);
        }
        if (($difX > plotSize - 1) || ($difZ > plotSize - 1)) {
            return new PlotCallback(null, CallbackIDs.ON_ROAD);
        }

        getLogger().info("Plot ist frei");
        return new PlotCallback(new Plot($X, $Z, position.getLevel()), CallbackIDs.FREE);
        // ToDo: provider
        //return provider.getPlot(position.getLevel(), $X, $Z);
    }

    public static QuadPlots getInstance() {
        return instance;
    }
}
