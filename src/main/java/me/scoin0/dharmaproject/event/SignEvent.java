package me.scoin0.dharmaproject.event;

import me.scoin0.dharmaproject.DharmaProject;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.SignChangeEvent;

public class SignEvent implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent event) {

        int x = DharmaProject.getPlugin().getConfig().getInt("signPos.x");
        int y = DharmaProject.getPlugin().getConfig().getInt("signPos.y");
        int z = DharmaProject.getPlugin().getConfig().getInt("signPos.z");

        World world = DharmaProject.getPlugin().getServer().getWorld(event.getPlayer().getWorld().getName());
        Block block = world.getBlockAt(x, y, z);
        BlockState state = block.getState();

        if (!(state instanceof Sign)){
            return;
        }

        //Sign sign = (Sign) state;
        //sign.setLine(0, "Testing");
        //sign.update();

    }
}
