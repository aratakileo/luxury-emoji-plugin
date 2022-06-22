package pextystudios.luxuryemojiplugin.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import org.bukkit.ChatColor;

public final class PageSwitcherUtil {
    private static String getTextJson(String string) {
        return "{\"text\":\"" + string + "\"}";
    }

    private static String getCommandActionJson(String command) {
        return "{\"action\":\"run_command\",\"value\":\"/" + command + "\"}";
    }

    private static String getShowTextActionJson(String text, String extra) {
        return "{\"action\":\"show_text\",\"value\":{\"text\":\"" + text + "\",\"extra\":[" + getTextJson(ChatColor.LIGHT_PURPLE + extra) + "]}}";
    }

    public static Component addPageSwitcher(String string, int page, int pages, String command) {
        String[] other_texts = string.split("%pageswitcher%");
        String json = "[" + getTextJson(other_texts[0]) + ",";

        if (other_texts.length == 1) {
            other_texts = new String[] {other_texts[0], ""};
        }

        if (page > 0) {
            json = json.concat("{\"text\":\"" + ChatColor.AQUA + "[<-]" + ChatColor.RESET + "\",\"clickEvent\":" + getCommandActionJson(command + " " + (page - 1)) + ",\"hoverEvent\":" + getShowTextActionJson("", "Предыдущая страница") + "}," + getTextJson(" ") + ",");
        }

        if (page < pages - 1) {
            json = json.concat("{\"text\":\"" + ChatColor.AQUA + "[->]" + ChatColor.RESET + "\",\"clickEvent\":" + getCommandActionJson(command + " " + (page + 1)) + ",\"hoverEvent\":" + getShowTextActionJson("", "Следующая страница") + "},");
        }

        return GsonComponentSerializer.gson().deserialize(json.concat(getTextJson(other_texts[1])) + "]");
    }
}
