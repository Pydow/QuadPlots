package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

public class ClaimCommand extends SubCommand {


    public ClaimCommand() {
        super("claim", "/p claim", new String[]{"c"}, "claim", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            PlotCallback cb = QuadPlots.instance.getPlotByPosition(player.getLocation());
            switch (cb.code) {
                case CallbackIDs.CLAIMED:
                    player.sendMessage(Language.get("already-claimed"));
                    break;
                    case CallbackIDs.ON_ROAD:
                        case CallbackIDs.UNKNOWN:
                            player.sendMessage(Language.get("not-on-a-plot"));
                            break;
                case CallbackIDs.FREE:

                    int maxPlots = QuadPlots.instance.getMaxPlotsOfPlayer(player);
                    int plotCount = QuadPlots.provider.getPlotsOfPlayer(player.getName()).size();

                    if (maxPlots != -1) {
                        if (plotCount >= maxPlots) {
                            player.sendMessage(Language.get("max-plots"));
                            return;
                        }
                    }

                    player.sendMessage(Language.get("claimed"));
                    QuadPlots.provider.claimPlot(cb.plot, player);
                    QuadPlots.instance.setPlotBorderBlocks(cb.plot, PlotSettings.wallBlockClaimed);
                    break;
            }
        }
    }
}
