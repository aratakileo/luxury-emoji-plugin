package pextystudios.luxuryemojiplugin;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import pextystudios.luxuryemojiplugin.command.EmojiListCommand;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public final class LuxuryEmojiPlugin extends JavaPlugin {
    private static LuxuryEmojiPlugin instance;
    private static LinkedHashMap<String, LinkedHashMap<String, String>> emoji_data;

    @Override
    public void onEnable() {
        // Plugin startup logic

        instance = this;

        saveDefaultConfig();
        saveResource("config.json", false);

        String emoji_data_file = new BufferedReader(
                new InputStreamReader(Objects.requireNonNull(LuxuryEmojiPlugin.getInstance().getResource("config.json")), StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        emoji_data = new Gson().fromJson(emoji_data_file, new TypeToken<LinkedHashMap<String, LinkedHashMap<String, String>>>(){}.getType());

        Bukkit.getPluginManager().registerEvents(new EventListener(), this);

        new EmojiListCommand();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LuxuryEmojiPlugin getInstance() {
        return instance;
    }

    public static LinkedHashMap<String, LinkedHashMap<String, String>> getEmojiData() {
        return emoji_data;
    }

    public static Collection<String> getEmojiList() {
        return getEmojiData().get("names").values();
    }

    public static Set<String> getEmojiNames() {
        return getEmojiData().get("names").keySet();
    }

    public static Set<String> getEmojiLiterals() {
        return getEmojiData().get("literals").keySet();
    }

    public static void sendMessage(Component component) {
        LuxuryEmojiPlugin.getInstance().getServer().sendMessage(component);
    }

    public static void sendMessage(String string) {
        LuxuryEmojiPlugin.sendMessage(Component.text(string));
    }

    public static void sendMessage(int i) {
        LuxuryEmojiPlugin.sendMessage(Component.text(String.valueOf(i)));
    }

    public static void sendMessage(Object object) {
        LuxuryEmojiPlugin.sendMessage(Component.text(object.toString()));
    }
}
