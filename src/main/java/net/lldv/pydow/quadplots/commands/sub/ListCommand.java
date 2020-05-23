package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.settings.Language;

import java.util.List;

public class ListCommand extends SubCommand {

    public ListCommand() {
        super("list", "/p list <optional: player>", new String[]{}, "list", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (args.length >= 2) {
            String target = args[1];
            List<Plot> plots = QuadPlots.provider.getPlotsOfPlayer(target);
            if (plots.size() == 0) {
                sender.sendMessage(Language.getAndReplace("no-plots-other", target));
                return;
            }
            sender.sendMessage(Language.getAndReplace("list-1", target, plotListToString(plots)));
        } else {
            if (sender.isPlayer()) {
                List<Plot> plots = QuadPlots.provider.getPlotsOfPlayer(sender.getName());
                if (plots.size() == 0) {
                    sender.sendMessage(Language.get("no-plots"));
                    return;
                }
                sender.sendMessage(Language.getAndReplace("list-1", sender.getName(), plotListToString(plots)));
            }
        }
    }

    private String plotListToString(List<Plot> plots) {
        StringBuilder sb = new StringBuilder();
        if (plots.size() == 0) return "";
        plots.forEach(p -> sb.append("(").append(p.x).append(";").append(p.z).append(")").append(", "));
        return sb.toString().substring(0, sb.toString().length() - 2);
    }
}
