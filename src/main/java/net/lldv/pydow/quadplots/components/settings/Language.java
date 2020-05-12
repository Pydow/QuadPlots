package net.lldv.pydow.quadplots.components.settings;

import cn.nukkit.utils.Config;
import net.lldv.pydow.quadplots.QuadPlots;

import java.util.HashMap;
import java.util.Map;

public class Language {

    public static HashMap<String, String> messages = new HashMap<>();
    public static String prefix;

    public static void init() {
        messages.clear();
        QuadPlots.getInstance().saveResource("messages.yml");
        Config m = new Config(QuadPlots.getInstance().getDataFolder() + "/messages.yml");
        for (Map.Entry<String, Object> map : m.getAll().entrySet()) {
            String key = map.getKey();
            if (map.getValue() instanceof String) {
                String val = (String) map.getValue();
                messages.put(key, val);
            }
        }
        prefix = m.getString("prefix");
    }

    public static String getAndReplace(String key, Object... replacements) {
        String message = get(key);
        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }
        return message;
    }

    public static String getAndReplaceNoPrefix(String key, Object... replacements) {
        String message = getNoPrefix(key);
        int i = 0;
        for (Object replacement : replacements) {
            message = message.replace("[" + i + "]", String.valueOf(replacement));
            i++;
        }
        return message;
    }

    public static String get(String key) {
        return prefix.replace("&", "ยง") + messages.getOrDefault(key, "null").replace("&", "ยง");
    }

    public static String getNoPrefix(String key) {
        return messages.getOrDefault(key, "null").replace("&", "ยง");
    }

}
