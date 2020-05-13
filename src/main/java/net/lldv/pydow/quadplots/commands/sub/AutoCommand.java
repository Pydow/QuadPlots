package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

import java.util.concurrent.CompletableFuture;

public class AutoCommand extends SubCommand {

    public AutoCommand() {
        super("auto", "/p auto", new String[]{"a"}, "auto", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            if (player.getLevel().getName().equalsIgnoreCase(PlotSettings.world)) {
                player.sendMessage(Language.get("searching-plot"));
                CompletableFuture.runAsync(() -> {
                    try {
                        PlotCallback cb = QuadPlots.provider.findFreePlot(player.getLevel(), 0, 0);
                        player.teleport(QuadPlots.instance.getPlotPosition(cb.plot).add(0, 2, 0));
                        player.sendMessage(Language.get("plot-found"));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
            } else player.sendMessage(Language.get("not-in-a-plot-world"));
        }
    }
}
