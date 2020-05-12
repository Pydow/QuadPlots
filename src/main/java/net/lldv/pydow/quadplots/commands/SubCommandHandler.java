package net.lldv.pydow.quadplots.commands;

import cn.nukkit.command.CommandSender;
import net.lldv.pydow.quadplots.components.settings.Language;

import java.util.HashMap;

public class SubCommandHandler {

    public static HashMap<String, SubCommand> commands = new HashMap<>();
    public static String permRoot = "quadplots.";

    public static void register(String name, SubCommand subCommand) {
        commands.put(name, subCommand);
    }

    public static void handle(CommandSender sender, String s, String[] args) {
        if (args.length > 0) {
            SubCommand sc = getCommand(args[0]);
            if (sc != null) {
                if (!sender.isOp() && sc.needPermission) {
                    if (!sender.hasPermission(getPerm(permRoot, sc.permNode))) {
                        sender.sendMessage(Language.get("no-permission"));
                        return;
                    }
                }
                sc.execute(sender, s, args);
            } else showHelp(sender);
        } else {
            showHelp(sender);
        }
    }

    public static SubCommand getCommand(String search) {
        for (SubCommand sc : commands.values()) {
            if (sc.name.equalsIgnoreCase(search)) return sc;
            if (sc.isAlias(search)) return sc;
        }
        return null;
    }

    public static void showHelp(CommandSender sender) {
        sender.sendMessage(Language.get("command-not-found"));
    }

    public static String getPerm(String root, String node) {
        return root + node;
    }

}
