package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.block.Block;
import cn.nukkit.command.CommandSender;
import cn.nukkit.player.Player;
import cn.nukkit.utils.Identifier;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;

public class BorderCommand extends SubCommand {

    public BorderCommand() {
        super("border", "/p border <id>", new String[]{"rand"}, "border", true);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            if (args.length >= 2) {
                PlotCallback cb = QuadPlots.instance.getPlotByPosition(player.getLocation());
                boolean isBypassing = QuadPlots.instance.isBypassing(player.getName());

                if (cb.plot != null) {
                    if (isBypassing || cb.code == CallbackIDs.CLAIMED) {
                        Plot plot = cb.plot;
                        if (isBypassing || plot.owner.equalsIgnoreCase(player.getName())) {
                            try {
                                Identifier blockID = Identifier.fromString(args[1]);
                                QuadPlots.instance.setPlotBorderBlocks(plot, Block.get(blockID));
                                player.sendMessage(Language.getAndReplace("border-changed", Block.get(blockID)));
                            } catch (Exception ex) {
                                player.sendMessage(Language.get("id-needs-to-be-number"));
                            }
                        }
                    } else player.sendMessage(Language.get("no-plot-permission"));
                } else player.sendMessage(Language.get("not-on-a-plot"));
            } else player.sendMessage(usage);
        }
    }
}
