package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;

public class ResetCommand extends SubCommand {

    public ResetCommand() {
        super("reset", "/p reset <confirm>", new String[]{"delete"}, "reset", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            PlotCallback cb = QuadPlots.instance.getPlotByPosition(player.getLocation());
            Plot plot = cb.plot;
            boolean isBypassing = QuadPlots.instance.isBypassing(player.getName());

            if (plot.isClaimed()) {
                if (plot.owner.equalsIgnoreCase(player.getName()) || isBypassing) {
                    if (args.length >= 2 && args[1].equalsIgnoreCase("confirm")) {
                        Location pos = QuadPlots.instance.getPlotPosition(plot).add(0, 2, 0);
                        player.teleport(pos);
                        QuadPlots.instance.clearPlot(new Plot(plot.x, plot.z, plot.world));
                        QuadPlots.provider.resetPlot(plot);
                        sender.sendMessage(Language.get("resetted"));
                    } else sender.sendMessage(Language.get("reset-confirm"));
                } else sender.sendMessage(Language.get("not-your-plot"));
            } else sender.sendMessage(Language.get("not-your-plot"));
        }
    }
}
