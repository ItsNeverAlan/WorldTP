package org.imanity.worldtp;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.stream.Collectors;

public final class WorldTP extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("worldtp")
                .setExecutor(new CommandExecutor() {
                    @Override
                    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

                        if (!sender.hasPermission("worldtp.admin")) {
                            sender.sendMessage(ChatColor.RED + "You have no permission!");
                            return true;
                        }

                        if (sender instanceof Player) {
                            if (args.length > 0) {
                                if (args[0].equalsIgnoreCase("list")) {
                                    sender.sendMessage(ChatColor.YELLOW + "Worlds:");
                                    sender.sendMessage(ChatColor.AQUA + Bukkit.getWorlds().stream().map(World::getName).collect(Collectors.joining(", ")));
                                    return true;
                                }
                                Player player = (Player) sender;
                                World world = Bukkit.getWorld(args[0]);

                                if (world == null) {
                                    player.sendMessage(ChatColor.RED + "The world is not valid!");
                                    return true;
                                }

                                player.teleport(world.getSpawnLocation());
                            } else {
                                sender.sendMessage(ChatColor.RED + "/worldtp <world>");
                            }
                        } else {
                            sender.sendMessage("This comamnd must be executed by a player.");
                        }
                        return true;
                    }
                });
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
