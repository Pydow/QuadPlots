package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.tools.TextTools;

public class InfoCommand extends SubCommand {

    public InfoCommand() {
        super("info", "/p info", new String[]{"i"}, "info", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            PlotCallback cb = QuadPlots.instance.getPlotByPosition(player.getLocation());
            Plot plot = cb.plot;
            switch (cb.code) {
                case CallbackIDs.CLAIMED:
                    player.sendMessage(Language.getAndReplace("info-1", cb.plot.owner));
                    player.sendMessage(Language.getAndReplace("info-2", TextTools.stringifyList(plot.members)));
                    player.sendMessage(Language.getAndReplace("info-3", TextTools.stringifyList(plot.helpers)));
                    player.sendMessage(Language.getAndReplace("info-4", TextTools.stringifyList(plot.denied)));

                    break;
                case CallbackIDs.ON_ROAD:
                    case CallbackIDs.UNKNOWN:
                        player.sendMessage(Language.get("not-on-a-plot"));
                        break;
                default:
                    player.sendMessage(Language.getAndReplace("info-free", plot.x, plot.z));
            }
            /*
            if (plot != null) {
                player.sendMessage(Language.getAndReplace("infoLine1", plot.x, plot.z));
            } else player.sendMessage(Language.get("not-on-a-plot"));*/
        }
    }
}
