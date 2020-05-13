package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.settings.Language;

import java.util.List;

public class HomeCommand extends SubCommand {

    public HomeCommand() {
        super("home", "/p home <optional: number> <optional: player>", new String[]{"h"}, "home", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            String target = player.getName();
            int id = 1;
            boolean otherPlayer = false;

            try {
                if (args.length >= 2) {
                    id = Integer.parseInt(args[1]);
                }
            } catch (NumberFormatException ex) {
                try {
                    target = args[1];
                    Player preTarget = QuadPlots.instance.getServer().getPlayer(args[1]);
                    if (preTarget != null) target = preTarget.getName();
                    otherPlayer = true;

                    if (args.length >= 3) id = Integer.parseInt(args[2]);

                } catch (NumberFormatException exx) {
                    player.sendMessage(Language.get("id-needs-to-be-number"));
                    return;
                }
            }

            List<Plot> plots = QuadPlots.provider.getPlotsOfPlayer(target);

            if (plots.size() > 0) {

                    if (plots.size() < id) {
                        if (otherPlayer) player.sendMessage(Language.getAndReplaceNoPrefix("no-plot-with-id-other", target, id));
                        if(!otherPlayer) sender.sendMessage(Language.getAndReplace("no-plot-with-id", id));
                        return;
                    }

                    Plot plot = plots.get(id - 1);

                    if (plot != null) {
                        Location position = QuadPlots.instance.getPlotPosition(plot);
                        player.teleport(position.add(0, 2, 0));
                        if(otherPlayer) sender.sendMessage(Language.getAndReplace("home-teleported-other", id, target));
                        if(!otherPlayer) sender.sendMessage(Language.getAndReplace("home-teleported", id));
                    }


            } else {
                if (otherPlayer) sender.sendMessage(Language.getAndReplace("no-plots-other"));
                if (!otherPlayer) sender.sendMessage(Language.get("no-plots"));
            }
        }
    }
}
