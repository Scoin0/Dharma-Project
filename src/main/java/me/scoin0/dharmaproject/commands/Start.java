package me.scoin0.dharmaproject.commands;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Start  implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        World world = (World) player.getWorld();
        BukkitWorld world1 = new BukkitWorld(world);
        File file = new File("plugins/dharma-project/airship-e80.schem");

        // Begin the countdown!
        if (command.getName().equalsIgnoreCase("start")) {

            player.sendMessage(ChatColor.GOLD + "Starting Process...");
            beginCountDown();

            ClipboardFormat format = ClipboardFormats.findByFile(file);
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                Clipboard clipboard = reader.read();
                try (EditSession edit = WorldEdit.getInstance().newEditSession(world1)){
                    Operation operation = new ClipboardHolder(clipboard)
                            .createPaste(edit)
                            .to(BlockVector3.at(player.getLocation().getX() + 5, player.getLocation().getY() + 5, player.getLocation().getZ() + 5))
                            .build();
                    Operations.complete(operation);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException | WorldEditException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public void beginCountDown() {

    }
}
