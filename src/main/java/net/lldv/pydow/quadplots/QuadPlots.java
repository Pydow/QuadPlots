package net.lldv.pydow.quadplots;

import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Identifier;
import net.lldv.pydow.quadplots.components.generator.QuadPlotGen;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

public class QuadPlots extends PluginBase {

    public static QuadPlots instance;
    public static Identifier id = Identifier.fromString("quadplots:default");

    @Override
    public void onLoad() {
        instance = this;
        PlotSettings.load();
        getServer().getGeneratorRegistry().register(id, new QuadPlotGen(), 0);
    }

    @Override
    public void onEnable() {

    }
}
