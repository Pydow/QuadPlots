package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;


public class RemoveHelperCommand extends SubCommand {

    public RemoveHelperCommand() {
        super("removehelper", "/p removehelper <player>", new String[]{}, "removehelper", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            PlotCallback cb = QuadPlots.instance.getPlotByPosition(player.getLocation());

            boolean isBypassing = QuadPlots.instance.isBypassing(player.getName());

            if (cb.code == CallbackIDs.CLAIMED) {
                if (cb.plot.owner.equalsIgnoreCase(player.getName()) || isBypassing) {
                    if (args.length >= 2) {
                        String target = args[1];

                        Player preTarget = QuadPlots.instance.getServer().getPlayer(target);
                        if (preTarget != null) target = preTarget.getName();

                        if (cb.plot.helpers.contains(target)) {
                            // helper-added
                            QuadPlots.provider.removeHelper(cb.plot, target);
                            sender.sendMessage(Language.getAndReplace("helper-removed", target));
                        } else sender.sendMessage(Language.getAndReplace("not-helper", target));

                    } else sender.sendMessage(usage);
                } else sender.sendMessage(Language.get("not-your-plot"));
            } else {
                if (cb.plot == null) sender.sendMessage(Language.get("not-on-a-plot"));
                if (cb.plot != null) sender.sendMessage(Language.get("not-your-plot"));
            }
        }
    }

}
