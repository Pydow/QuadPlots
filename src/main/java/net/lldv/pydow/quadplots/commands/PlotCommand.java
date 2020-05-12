package net.lldv.pydow.quadplots.commands;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandFactory;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import net.lldv.pydow.quadplots.QuadPlots;

public class PlotCommand extends PluginCommand<QuadPlots> implements CommandFactory {

    public PlotCommand() {
        super("plot", QuadPlots.getInstance());
        setDescription("Plot Command");
        setAliases(new String[]{"p"});
        setUsage("/p info");
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        SubCommandHandler.handle(sender, s, args);
        return false;
    }

    @Override
    public Command create(String s) {
        return this;
    }
}
