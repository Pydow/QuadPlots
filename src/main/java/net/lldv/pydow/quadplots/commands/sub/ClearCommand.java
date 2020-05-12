package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;

import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;

public class ClearCommand extends SubCommand {

    public ClearCommand() {
        super("clear", "/p clear <confirm>", new String[]{}, "clear", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            PlotCallback plot = QuadPlots.instance.getPlotByPosition(player.getLocation());

            if (plot.plot == null) {
                player.sendMessage(Language.get("not-on-a-plot"));
                return;
            }

            if (!QuadPlots.instance.isBypassing(player.getName())) {
                if (plot.plot.isClaimed()) {
                    if (!plot.plot.owner.equalsIgnoreCase(player.getName())) {
                        player.sendMessage(Language.get("not-your-plot"));
                        return;
                    }
                } else {
                    player.sendMessage(Language.get("not-your-plot"));
                    return;
                }
            }

            boolean confirmed = false;
            if (args.length >= 2 && args[1].equalsIgnoreCase("confirm")) confirmed = true;

            if (confirmed) {
                Location pos = QuadPlots.instance.getPlotPosition(plot.plot).add(0, 2, 0);
                player.teleport(pos);
                QuadPlots.instance.clearPlot(plot.plot);
                player.sendMessage(Language.get("clear"));
            } else player.sendMessage(Language.get("clearConfirm"));
        }
    }
}
