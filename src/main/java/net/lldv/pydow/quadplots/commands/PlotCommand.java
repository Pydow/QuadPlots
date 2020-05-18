package net.lldv.pydow.quadplots.commands;

import cn.nukkit.command.CommandSender;
import cn.nukkit.command.PluginCommand;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.components.tools.Command;

public class PlotCommand extends PluginCommand<QuadPlots> {

    public PlotCommand(QuadPlots owner) {
        super(owner, Command.create("plot", "Plot Command",
                new String[]{},
                new String[]{"p"},
                new CommandParameter[]{new CommandParameter("subcommand", false, new String[]{"claim", "auto", "info", "help", "bypass", "home", "reset", "clear", "dispose", "h"})},
                new CommandParameter[]{
                        new CommandParameter("subcommand", false, new String[]{"home", "h"}),
                        new CommandParameter("spieler|nummer", CommandParamType.STRING, false),
                        new CommandParameter("nummer", CommandParamType.STRING, false),
                },
                new CommandParameter[]{
                        new CommandParameter("subcommand", false, new String[]{"deny", "undeny", "addmember", "addhelper", "removehelper", "removemember"}),
                        new CommandParameter("spieler", CommandParamType.STRING, false)
                }
        ));
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        SubCommandHandler.handle(sender, s, args);
        return false;
    }

}
