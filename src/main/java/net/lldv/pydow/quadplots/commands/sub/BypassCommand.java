package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.commands.SubCommand;
import net.lldv.pydow.quadplots.components.settings.Language;

public class BypassCommand extends SubCommand {

    public BypassCommand() {
        super("bypass", "/p bypass", new String[]{"ignore"}, "bypass", true);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            if (QuadPlots.instance.isBypassing(sender.getName())) {
                QuadPlots.bypassPlayers.remove(sender.getName());
                sender.sendMessage(Language.get("bypass-off"));
            } else {
                QuadPlots.bypassPlayers.add(sender.getName());
                sender.sendMessage(Language.get("bypass-on"));
            }
        }
    }
}
