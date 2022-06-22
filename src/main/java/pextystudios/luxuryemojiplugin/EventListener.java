package pextystudios.luxuryemojiplugin;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.RemoteServerCommandEvent;
import org.bukkit.event.server.ServerCommandEvent;

import static pextystudios.luxuryemojiplugin.util.FormatUtil.format;

public class EventListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent e) {
        e.message(format(e.message()));
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        int i = 0;
        for (Component line: e.lines()) {
            e.line(i, format(line));

            i++;
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        e.joinMessage(format(e.joinMessage()));
    }

    @EventHandler
    public void onLeft(PlayerQuitEvent e) {
        e.quitMessage(format(e.quitMessage()));
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        e.leaveMessage(format(e.leaveMessage()));
    }

    @EventHandler
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        e.cancelMessage(format(e.cancelMessage()));
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent e) {
        e.kickMessage(format(e.kickMessage()));
    }

    @EventHandler
    public void onRemoteServerCommand(RemoteServerCommandEvent e) {
        e.setCommand(format(e.getCommand()));
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent e) {
        e.setCommand(format(e.getCommand()));
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent e) {
        e.setMessage(format(e.getMessage()));
    }

    @EventHandler
    public void onPickUpItem(EntityPickupItemEvent e) {
        e.getItem().customName(format(e.getItem().customName()));
    }
}
