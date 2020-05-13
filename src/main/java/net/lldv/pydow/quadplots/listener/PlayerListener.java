package net.lldv.pydow.quadplots.listener;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.player.Player;
import net.lldv.pydow.quadplots.QuadPlots;
import net.lldv.pydow.quadplots.components.PlotCallback;
import net.lldv.pydow.quadplots.components.settings.PlotSettings;

public class PlayerListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (PlotSettings.world.equalsIgnoreCase(event.getEntity().getLevel().getName())) {
            if (event.getEntity() instanceof Player) {
                Player player = (Player) event.getEntity();
                if (event instanceof EntityDamageByEntityEvent) {
                    EntityDamageByEntityEvent ev = (EntityDamageByEntityEvent) event;
                    if (ev.getDamager() instanceof Player) {
                        PlotCallback cb = QuadPlots.instance.getPlotByPosition(player.getLocation());
                        if (cb.plot == null) {
                            event.setCancelled(true);
                            // Pvp on road off
                            return;
                        } else {
                            // ToDo: check if pvp is enabled on plot lol
                            event.setCancelled(true);
                        }
                    }
                } else event.setCancelled(true);
            }
        }
    }

}
