package net.lldv.pydow.quadplots.commands;

import cn.nukkit.command.CommandSender;
import net.lldv.pydow.quadplots.QuadPlots;

public class SubCommand {

    public String name;
    public String[] aliases;
    public String permNode;
    public String usage;
    public boolean needPermission;

    public SubCommand(String name, String usage, String[] aliases, String permNode, boolean needPermission) {
        this.name = name;
        this.aliases = aliases;
        this.permNode = permNode;
        this.needPermission = needPermission;
        this.usage = usage;
    }

    public void execute(CommandSender sender, String s, String[] args) {

    }

    public boolean isAlias(String cmd) {
        for (String alias : aliases) if (alias.equalsIgnoreCase(cmd)) return true;
        return false;
    }

    public QuadPlots getPlugin() {
        return QuadPlots.getInstance();
    }

}
