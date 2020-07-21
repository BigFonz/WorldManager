package com.foenixe.worldmanager.command;

import com.foenixe.worldmanager.WorldManager;
import com.foenixe.worldmanager.util.CC;
import com.foenixe.worldmanager.util.ErrorMessage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WorldCommand implements CommandExecutor {

    private static final List<String> arguments = new ArrayList<>();

    public WorldCommand() {
        arguments.add("load");
        arguments.add("unload");
        arguments.add("teleport");
        arguments.add("tp");
        arguments.add("list");
        arguments.add("ls");
        arguments.add("help");
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (args.length == 2) {
                switch (args[0]) {
                    case "load":
                        if (Bukkit.getWorld(args[1]) == null) {
                            if (isUnloadedWorld(args[1])) {
                                p.sendMessage(CC.translate("&aLoading..."));
                                Bukkit.createWorld(new WorldCreator(args[1]));
                                p.sendMessage(CC.translate("&aSuccessfully loaded world&7: &6" + args[1]));
                                break;
                            }
                            p.sendMessage(ErrorMessage.UNLOADED_WORLD_NOT_FOUND+"");
                            break;
                        }
                        p.sendMessage(ErrorMessage.WORLD_IS_LOADED+"");
                        break;
                    case "unload":
                        if (Bukkit.getWorld(args[1]) != null) {
                            World world = Bukkit.getWorld(args[1]);
                            if (world.getPlayers().isEmpty()) {
                                p.sendMessage(CC.translate("&aUnloading..."));
                                Bukkit.unloadWorld(world, true);
                                p.sendMessage(CC.translate("&aSuccessfully unloaded world&7: &6" + world.getName()));
                                break;
                            }
                            p.sendMessage(ErrorMessage.IN_UNLOADING_WORLD+"");
                            break;
                        }
                        p.sendMessage(ErrorMessage.WORLD_NOT_FOUND+"");
                        break;
                    case "tp":
                    case "teleport":
                        World world = Bukkit.getWorld(args[1]);
                        if (world != null) {
                            p.teleport(world.getSpawnLocation());
                            p.sendMessage(CC.translate("&aTeleporting to world&7: &6" + world.getName()));
                            break;
                        }
                        p.sendMessage(ErrorMessage.WORLD_NOT_FOUND+"");
                        break;
                    default:
                        p.sendMessage(ErrorMessage.INVALID_ARGUMENTS+"");
                }
            } else if (args.length == 1) {
               if (args[0].equalsIgnoreCase("list") || args[0].equals("ls")) {
                   List<World> allWorlds = Bukkit.getWorlds();
                   String worlds = CC.translate("&7[");
                   for (World w : Bukkit.getWorlds()) {
                       worlds += CC.translate("&a" + w.getName() + (allWorlds.get(allWorlds.size() - 1).equals(w) ? "" : "&7, "));
                   }
                   worlds += CC.translate("&7]");
                   p.sendMessage(CC.translate("&6Current loaded worlds&7: " + worlds));
               } else if (args[0].equalsIgnoreCase("help")) {
                   p.sendMessage(CC.translate("&a/world load&7: &6Loads the specified world."));
                   p.sendMessage(CC.translate("&a/world unload&7: &6Unloads the specified world."));
                   p.sendMessage(CC.translate("&a/world teleport&7: &6Teleports the to the world's default spawnpoint"));
                   p.sendMessage(CC.translate("&a/world list&7: &6Lists all loaded worlds."));
                   p.sendMessage(CC.translate("&a/world help&7: &6Shows this page."));
               } else {
                   p.sendMessage(ErrorMessage.INVALID_ARGUMENTS+"");
               }

            } else {
                p.sendMessage(ErrorMessage.INVALID_ARGUMENTS+"");
            }

        } else {
            sender.sendMessage(ErrorMessage.PLAYER_ONLY+"");
        }

        return true;
        
    }

    public static class WorldCommandTabCompleter implements TabCompleter {
        @Override
        public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
            List<String> returningArgs = new ArrayList<>();
            // TODO:
            if (args.length == 1) {
                for (String sub : arguments) {
                    if (sub.startsWith(args[args.length - 1])) {
                        returningArgs.add(sub);
                    }
                }
                return returningArgs;
            } else if (args.length == 2) {
                switch (args[0]) {
                    case "unload":
                        for (World world : Bukkit.getWorlds()) {
                            returningArgs.add(world.getName());
                        }
                        return returningArgs;
                    case "load":
                        return getUnloadedWorlds();
                }
            }

            return returningArgs;
        }
    }

    private boolean isUnloadedWorld(String world) {
        File server;

        try {
            server = new File(Bukkit.getWorldContainer() + "/" + world);
        } catch (NullPointerException e) {
            return false;
        }
        return Arrays.asList(server.list()).contains("level.dat");
    }

    private static List<String> getUnloadedWorlds() {
        List<String> worlds = new ArrayList<>();
        File server;

        for (File file : Bukkit.getWorldContainer().listFiles()) {
            if (file.isDirectory() && Arrays.asList(file.list()).contains("level.dat") && Bukkit.getWorld(file.getName()) == null) {
                worlds.add(file.getName());
            }
        }

        return worlds;
    }
    
}
