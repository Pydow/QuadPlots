package net.lldv.pydow.quadplots;

import cn.nukkit.block.Block;
import cn.nukkit.level.Level;
import cn.nukkit.level.Location;
import cn.nukkit.player.Player;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.registry.CommandRegistry;
import cn.nukkit.utils.Identifier;
import net.lldv.pydow.quadplots.commands.PlotCommand;
import net.lldv.pydow.quadplots.commands.SubCommandHandler;
import net.lldv.pydow.quadplots.commands.sub.*;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.Plot;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.generator.QuadPlotGen;
import net.lldv.pydow.quadplots.components.managers.HelpFormManager;
import net.lldv.pydow.quadplots.components.provider.Provider;
import net.lldv.pydow.quadplots.components.provider.YAMLProvider;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;
import net.lldv.pydow.quadplots.components.tasks.PlotClearTask;
import net.lldv.pydow.quadplots.components.tasks.SetBorderTask;
import net.lldv.pydow.quadplots.listener.BlockListener;
import net.lldv.pydow.quadplots.listener.MovementListener;
import net.lldv.pydow.quadplots.listener.PlayerListener;

import java.util.ArrayList;
import java.util.Map;

public class QuadPlots extends PluginBase {

    public static QuadPlots instance;
    public static Identifier id = Identifier.fromString("quadplots:default");
    public static ArrayList<String> bypassPlayers = new ArrayList<>();
    public static Provider provider;
    public static int defaultPlotLimit;

    @Override
    public void onLoad() {
        instance = this;
        PlotSettings.load();
        Language.init();
        getServer().getGeneratorRegistry().register(id, new QuadPlotGen(), 0);
        registerSubCommands();
        CommandRegistry cr = getServer().getCommandRegistry();
        cr.register(this, new PlotCommand(this));
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();

        registerHelpPages();

        provider = new YAMLProvider();
        provider.init();

        defaultPlotLimit = getConfig().getInt("default-plot-limit");

        getServer().getPluginManager().registerEvents(new MovementListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
    }

    public void registerHelpPages() {
        HelpFormManager.addCaterogy(Language.getNoPrefix("help-getting-started"), Language.getNoPrefix("help-getting-started-content"));
        HelpFormManager.addCaterogy(Language.getNoPrefix("help-deny"), Language.getNoPrefix("help-deny-content"));
        HelpFormManager.addCaterogy(Language.getNoPrefix("help-clear-reset"), Language.getNoPrefix("help-clear-reset-content"));
        HelpFormManager.addCaterogy(Language.getNoPrefix("help-teleport"), Language.getNoPrefix("help-teleport-content"));
        HelpFormManager.addCaterogy(Language.getNoPrefix("help-helpers-members"), Language.getNoPrefix("help-helpers-members-content"));
    }

    public void registerSubCommands() {
        SubCommandHandler.register("info", new InfoCommand());
        SubCommandHandler.register("border", new BorderCommand());
        SubCommandHandler.register("clear", new ClearCommand());
        SubCommandHandler.register("bypass", new BypassCommand());
        SubCommandHandler.register("home", new HomeCommand());
        SubCommandHandler.register("claim", new ClaimCommand());
        SubCommandHandler.register("addhelper", new AddHelperCommand());
        SubCommandHandler.register("addmember", new AddMemberCommand());
        SubCommandHandler.register("auto", new AutoCommand());
        SubCommandHandler.register("deny", new DenyCommand());
        SubCommandHandler.register("help", new HelpCommand());
        SubCommandHandler.register("removehelper", new RemoveHelperCommand());
        SubCommandHandler.register("removemember", new RemoveMemberCommand());
        SubCommandHandler.register("reset", new ResetCommand());
        SubCommandHandler.register("undeny", new UndenyCommand());
        SubCommandHandler.register("warp", new WarpCommand());
        SubCommandHandler.register("dispose", new DisposeCommand());
        SubCommandHandler.register("list", new ListCommand());
    }

    public boolean isBypassing(String player) {
        return bypassPlayers.contains(player);
    }


    public Location getPlotPosition(Plot plot) {
        int plotSize = PlotSettings.plotSize;
        int roadWidth = PlotSettings.roadWidth;
        int totalSize = plotSize + roadWidth;
        int x = totalSize * plot.x;
        int z = totalSize * plot.z;
        Level level = plot.world;
        return Location.from(x, PlotSettings.groundHeight, z, level);
    }

    public void clearPlot(Plot plot) {
        getServer().getScheduler().scheduleDelayedTask(this, new PlotClearTask(plot), 1);
        if (plot.isClaimed()) {
            setPlotBorderBlocks(plot, PlotSettings.wallBlockClaimed);
        } else setPlotBorderBlocks(plot, PlotSettings.wallBlock);
    }

    public void setPlotBorderBlocks(Plot $plot, Block $block) {
        getServer().getScheduler().scheduleDelayedTask(this, new SetBorderTask($plot, $block), 1);
    }

    public int getMaxPlotsOfPlayer(Player player) {
        if (player.isOp()) return -1;
        int maxShops = defaultPlotLimit;
        for (Map.Entry<String, Boolean> perm : player.addAttachment(instance).getPermissions().entrySet()) {
            if (perm.getValue()) {
                if (perm.getKey().contains("quadplots.claimplots.")) {
                    String max = perm.getKey().replace("quadplots.claimplots.", "");
                    if (max.equalsIgnoreCase("unlimited")) {
                        return -1;
                    } else {
                        try {
                            int num = Integer.parseInt(max);
                            if (num > maxShops) maxShops = num;
                        } catch (NumberFormatException ignored) {
                        }
                    }
                }
            }
        }
        return maxShops;
    }

    public PlotCallback getPlotByPosition(Location position) {
        double $x = position.getX();
        double $z = position.getZ();
        int $X;
        int $Z;
        double $difX;
        double $difZ;

        String levelName = position.getLevel().getName();
        if (!levelName.equalsIgnoreCase(PlotSettings.world)) return new PlotCallback(null, CallbackIDs.ON_ROAD);

        int plotSize = PlotSettings.plotSize;
        int roadWidth = PlotSettings.roadWidth;
        int totalSize = plotSize + roadWidth;
        if ($x >= 0) {
            $X = (int) Math.floor($x / totalSize);
            $difX = $x % totalSize;
        } else {
            $X = (int) Math.ceil(($x - plotSize + 1) / totalSize);
            $difX = Math.abs(($x - plotSize + 1) % totalSize);
        }
        if ($z >= 0) {
            $Z = (int) Math.floor($z / totalSize);
            $difZ = $z % totalSize;
        } else {
            $Z = (int) Math.ceil(($z - plotSize + 1) / totalSize);
            $difZ = Math.abs(($z - plotSize + 1) % totalSize);
        }
        if (($difX > plotSize - 1) || ($difZ > plotSize - 1)) {
            return new PlotCallback(null, CallbackIDs.ON_ROAD);
        }

        return provider.getPlot(position.getLevel(), $X, $Z);
    }

    public static QuadPlots getInstance() {
        return instance;
    }
}
