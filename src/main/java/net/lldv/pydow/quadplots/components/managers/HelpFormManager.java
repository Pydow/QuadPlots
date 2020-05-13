package net.lldv.pydow.quadplots.components.managers;

import cn.nukkit.form.Form;
import cn.nukkit.form.SimpleForm;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.player.Player;

import java.util.HashMap;

public class HelpFormManager {

    public static HashMap<String, String> caterogies = new HashMap<>();

    public static void sendHelpFrom(Player player) {
        SimpleForm.SimpleFormBuilder formBuilder = Form.simple().title("QuadPlots - Hilfe");

        for (String cat : caterogies.keySet()) {
            formBuilder.button(cat, (formPlayer) -> {
                sendHelpPage(formPlayer, cat);
            });
        }

        player.showFormWindow(formBuilder.build());
    }

    public static void sendHelpPage(Player player, String caterogy) {
        SimpleForm form = Form.simple()
                .title(caterogy)
                .content(caterogies.get(caterogy))
                .onClose(HelpFormManager::sendHelpFrom)
                .build();
        player.showFormWindow(form);
    }

    public static void addCaterogy(String title, String content) {
        caterogies.put(title, content);
    }

}
