package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

public class DisposeCommand extends SubCommand {

    public DisposeCommand() {
        super("dispose", "/p dispose", new String[]{"unclaim"}, "dispose", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            PlotCallback cb = QuadPlots.instance.getPlotByPosition(player.getLocation());
            Plot plot = cb.plot;
            boolean isBypassing = QuadPlots.instance.isBypassing(player.getName());

            if (plot.isClaimed()) {
                if (isBypassing || plot.owner.equalsIgnoreCase(player.getName())) {
                    if (args.length >= 2 && args[1].equalsIgnoreCase("confirm")) {
                        Location pos = QuadPlots.instance.getPlotPosition(plot).add(0, 2, 0);
                        player.teleport(pos);
                        QuadPlots.getInstance().setPlotBorderBlocks(plot, PlotSettings.wallBlock);
                        QuadPlots.provider.resetPlot(plot);
                        sender.sendMessage(Language.get("disposed"));
                    } else sender.sendMessage(Language.get("dispose-confirm"));
                } else sender.sendMessage(Language.get("not-your-plot"));
            } else sender.sendMessage(Language.get("not-your-plot"));
        }
    }
}
