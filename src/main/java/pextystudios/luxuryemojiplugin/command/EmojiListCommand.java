package pextystudios.luxuryemojiplugin.command;

import com.google.common.collect.Lists;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import pextystudios.luxuryemojiplugin.LuxuryEmojiPlugin;

import java.util.List;

import static pextystudios.luxuryemojiplugin.util.FormatUtil.format;
import static pextystudios.luxuryemojiplugin.util.PageSwitcherUtil.addPageSwitcher;

public class EmojiListCommand extends AbstractCommand {
    private final static int max_emojis_in_page = 5;
    private final static int pages = LuxuryEmojiPlugin.getEmojiList().size() / max_emojis_in_page + (LuxuryEmojiPlugin.getEmojiList().size() % max_emojis_in_page == 0 ? 0 : 1);
    private final static int literal_pages = LuxuryEmojiPlugin.getEmojiLiterals().size() / max_emojis_in_page + (LuxuryEmojiPlugin.getEmojiLiterals().size() % max_emojis_in_page == 0 ? 0 : 1);

    public EmojiListCommand() {
        super("emojilist");
    }

    @Override
    public void execute(CommandSender commandSender, String label, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(
                    "Использование команды:\n/" + label + " <page>\n/" + label + " alt" + (
                            LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.literals-enabled")
                                    ? ("\n/" + label + " literals\n/" + label + " literals <page>") : ""
                    )
            );
            return;
        }

        if (args.length <= 2) {
            int page;

            try {
                page = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                if (args[0].equals("literals")) {
                    if (!LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.literals-enabled")) {
                        commandSender.sendMessage(format(ChatColor.GOLD + "Эта функция была отключена %eid:11%", true));
                        return;
                    }

                    if (args.length == 1) {
                        commandSender.sendMessage("Использование команды: /" + label + " literals <page>");
                        return;
                    }

                    try {
                        page = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e1) {
                        commandSender.sendMessage(format(ChatColor.RED + "Неверный аргумент команды %eid:11%", true));
                        return;
                    }

                    if (page < 0 || page >= literal_pages) {
                        commandSender.sendMessage(format(ChatColor.RED + "Неверное значение команды %eid:11% (значение не может быть меньше 0 или больше " + (literal_pages - 1) + ")", true));
                        return;
                    }

                    String literalsList = ChatColor.BOLD.toString() + ChatColor.GOLD + format("%eid:7%Доступные литералы%eid:6%", true) + ChatColor.RESET + "\n";

                    for (int i = page * max_emojis_in_page; i < Math.min(page * max_emojis_in_page + max_emojis_in_page, LuxuryEmojiPlugin.getEmojiLiterals().size()); i++) {
                        literalsList = literalsList.concat(LuxuryEmojiPlugin.getEmojiLiterals().toArray()[i] + " -> " + LuxuryEmojiPlugin.getEmojiData().get("literals").values().toArray()[i] + '\n');
                    }

                    commandSender.sendMessage(addPageSwitcher(literalsList + "Страница " + page + " из " + (literal_pages - 1) + " %pageswitcher%", page, literal_pages, label + " literals"));
                    return;
                }

                if (args[0].equals("alt")) {
                    if (!LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.index-enabled")) {
                        commandSender.sendMessage(format(ChatColor.GOLD + "Эта функция была отключена %eid:11%", true));
                        return;
                    }

                    if (args.length == 2) {
                        commandSender.sendMessage(format(ChatColor.RED + "Неверный формат команды %eid:11%", true));
                        return;
                    }

                    commandSender.sendMessage("Альтернативная форма записи: %eid:<index>%\nПример записи: %eid:1%\nПреобразуется в: " + format("%eid:1%", true));

                    return;
                }

                if (!(LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.index-enabled") || LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.names-enabled"))) {
                    commandSender.sendMessage(format(ChatColor.GOLD + "Эта функция была отключена %eid:11%", true));
                    return;
                }

                commandSender.sendMessage(format(ChatColor.RED + "Неверный аргумент команды %eid:11%", true));
                return;
            }

            if (!(LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.index-enabled") || LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.names-enabled"))) {
                commandSender.sendMessage(format(ChatColor.GOLD + "Эта функция была отключена %eid:11%", true));
                return;
            }

            if (args.length == 2) {
                commandSender.sendMessage(format(ChatColor.RED + "Неверный формат команды %eid:11%", true));
                return;
            }

            if (page < 0 || page >= pages) {
                commandSender.sendMessage(format(ChatColor.RED + "Неверное значение команды %eid:11% (значение не может быть меньше 0 или больше " + (pages - 1) + ")", true));
                return;
            }

            String emojiList = ChatColor.BOLD + "" + ChatColor.GOLD + format("%eid:7%Список эмодзи%eid:6%", true) + ChatColor.RESET + "\n";

            for (int i = page * max_emojis_in_page; i < Math.min(page * max_emojis_in_page + max_emojis_in_page, LuxuryEmojiPlugin.getEmojiList().size()); i++) {
                emojiList = emojiList.concat(
                        i + ". " + LuxuryEmojiPlugin.getEmojiList().toArray()[i]
                                + (LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.names-enabled") ? (" :" + LuxuryEmojiPlugin.getEmojiNames().toArray()[i] + ":\n") : "")
                        );
            }

            commandSender.sendMessage(addPageSwitcher(emojiList + "Страница " + page + " из " + (pages - 1) + " %pageswitcher%", page, pages, label));

            return;
        }

        commandSender.sendMessage(format(ChatColor.RED + "Неверный формат команды %eid:11%", true));
    }

    @Override
    public List<String> complete(CommandSender sender, String[] args) {
        if (args.length == 1) {
            List<String> res = Lists.newArrayList();

            if (LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.literals-enabled"))
                res.add("literals");

            if (LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.index-enabled"))
                res.add("alt");

            for (int i = 0; i < pages; i++) res.add(String.valueOf(i));

            return res;
        }

        if (args.length == 2 && args[0].equals("literals") && LuxuryEmojiPlugin.getInstance().getConfig().getBoolean("emoji.literals-enabled")) {
            List<String> res = Lists.newArrayList();

            for (int i = 0; i < literal_pages; i++) res.add(String.valueOf(i));

            return res;
        }

        return Lists.newArrayList();
    }
}
