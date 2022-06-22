package pextystudios.luxuryemojiplugin.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.configuration.file.FileConfiguration;
import pextystudios.luxuryemojiplugin.LuxuryEmojiPlugin;

import java.util.regex.Pattern;

public final class FormatUtil {
    public static String format(String string) {
        return format(string, false);
    }

    public static String format (String string, boolean root_access) {
        try {
            return _format(string, root_access);
        } catch (Exception e) {
            return string;
        }
    }

    private static String _format(String string, boolean root_access) {
        FileConfiguration mainConfig = LuxuryEmojiPlugin.getInstance().getConfig();

        if (mainConfig.getBoolean("emoji.names-enabled") || mainConfig.getBoolean("emoji.index-enabled") || root_access) {
            int i = 0;
            for (String emoji_name : LuxuryEmojiPlugin.getEmojiNames()) {
                String emoji = LuxuryEmojiPlugin.getEmojiData().get("names").get(emoji_name);

                if (mainConfig.getBoolean("emoji.names-enabled") || root_access && string.contains(':' + emoji_name + ':'))
                    string = string.replace(':' + emoji_name + ':', "§r§f" + emoji + "§r");

                if (mainConfig.getBoolean("emoji.index-enabled") || root_access && Pattern.compile("%eid\\s*:\\s*" + i + "%").matcher(string).find())
                    string = string.replaceAll("%eid\\s*:\\s*" + i + "%", "§r§f" + emoji + "§r");

                i++;
            }
        }

        if (mainConfig.getBoolean("emoji.literals-enabled") || root_access) {
            for (String emoji_literal : LuxuryEmojiPlugin.getEmojiLiterals()) {
                if (string.contains(emoji_literal))
                    string = string.replace(emoji_literal, "§r§f" + LuxuryEmojiPlugin.getEmojiData().get("literals").get(emoji_literal) + "§r");
            }
        }

        return string;
    }

    public static Component format(Component component) {
        return format(component, false);
    }
    public static Component format(Component component, boolean root_access) {
        if (component instanceof TextComponent) {
            component = Component.text(format(((TextComponent)component).content(), root_access));
        }
        return component;
    }
}
