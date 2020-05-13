package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

public class WarpCommand extends SubCommand {

    public WarpCommand() {
        super("warp", "/p warp <x> <z>", new String[]{""}, "warp", true);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            if (args.length >= 3) {
                try {
                    Player player = (Player) sender;

                    int x = Integer.parseInt(args[1]);
                    int z = Integer.parseInt(args[2]);

                    Plot dummyPlot = new Plot(x, z, QuadPlots.getInstance().getServer().getLevelByName(PlotSettings.world));

                    Location position = QuadPlots.getInstance().getPlotPosition(dummyPlot);

                    player.teleport(position);
                    sender.sendMessage(Language.getAndReplace("warped", x, z));
                } catch (NumberFormatException ex) {
                    sender.sendMessage(Language.get("warp-number-error"));
                }
            } else {
                sender.sendMessage(usage);
            }
        }
    }
}
