package net.lldv.pydow.quadplots.listener;

import cn.nukkit.event.Listener;

public class FormListener implements Listener {

    /*
    @EventHandler
    public void onForm(PlayerFormRespondedEvent event) {
        if (event.getWindow() instanceof FormWindowSimple) {
            FormWindowSimple form = (FormWindowSimple) event.getWindow();
            if (form.getResponse() == null) return;
            ElementButton button = form.getResponse().getClickedButton();
            if (form.getTitle().equalsIgnoreCase(Language.getNoPrefix("help-title"))) {
                HelpFormManager.sendHelpPage(event.getPlayer(), button.getText());
            } else if (HelpFormManager.caterogies.containsKey(form.getTitle())) {
                HelpFormManager.sendHelpFrom(event.getPlayer());
            }
        }
    }*/

}
