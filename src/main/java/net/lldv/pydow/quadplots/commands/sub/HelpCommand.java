package net.lldv.pydow.quadplots.commands.sub;

import cn.nukkit.command.CommandSender;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.commands.SubCommand;

public class HelpCommand extends SubCommand {

    public HelpCommand() {
        super("help", "/p help", new String[]{"hilfe"}, "help", false);
    }

    @Override
    public void execute(CommandSender sender, String s, String[] args) {
        if (sender.isPlayer()) {
            Player player = (Player) sender;
            // ToDo: helpform
            //HelpFormManager.sendHelpFrom(player);
        }
    }
}
