package net.lldv.pydow.quadplots.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerMoveEvent;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.components.CallbackIDs;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.Language;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

import java.util.concurrent.CompletableFuture;

public class MovementListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        if (event.getPlayer().getLevel().getName().equalsIgnoreCase(PlotSettings.world)) {
            if (QuadPlots.instance.isBypassing(event.getPlayer().getName())) return;
            CompletableFuture.runAsync(() -> {
                PlotCallback from = QuadPlots.instance.getPlotByPosition(event.getFrom());
                PlotCallback to = QuadPlots.instance.getPlotByPosition(event.getTo());
                if (from.plot == null && to.plot != null) {
                    if (to.code == CallbackIDs.CLAIMED) {
                        if (!to.plot.isAllowedToEnter(event.getPlayer().getName())) {
                            event.getPlayer().teleport(event.getFrom());
                            return;
                        }
                        event.getPlayer().sendPopup(Language.getAndReplaceNoPrefix("plot-entered-claimed", to.plot.x, to.plot.z, to.plot.owner));
                    } else event.getPlayer().sendPopup(Language.getAndReplaceNoPrefix("plot-entered-free", to.plot.x, to.plot.z));
                    //event.getPlayer().sendMessage("Du hast ein Plot betreten.");
                } else if (from.plot != null && to.plot == null) {
                    // ToDo: implement lol
                    //event.getPlayer().sendMessage("Du hast ein Plot verlassen.");
                }
            });
        }
    }


}
