package com.github.kbinani.holosportsfestival2023.relay;

import com.github.kbinani.holosportsfestival2023.Editor;
import com.github.kbinani.holosportsfestival2023.MiniGame;
import com.github.kbinani.holosportsfestival2023.Point3i;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPistonRetractEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.WHITE;

public class RelayEventListener implements MiniGame {
  public static final Component title = text("[Relay]", AQUA);
  static final Component prefix = title.append(text(" ", WHITE));
  private static final Point3i offset = new Point3i(0, 0, 0);

  private final @Nonnull World world;
  private final @Nonnull JavaPlugin owner;
  private final Set<Point3i> pistonPositions;

  public RelayEventListener(@Nonnull World world, @Nonnull JavaPlugin owner) {
    this.world = world;
    this.owner = owner;
    pistonPositions = new HashSet<>();
    pistonPositions.add(pos(57, 79, 57));
    pistonPositions.add(pos(59, 79, 57));
    pistonPositions.add(pos(61, 79, 57));
  }

  @Override
  public void miniGameReset() {

  }

  @Override
  public void miniGameClearItem(Player player) {

  }

  @EventHandler
  @SuppressWarnings("unused")
  public void onItemSpawn(ItemSpawnEvent e) {
    var location = e.getLocation();
    if (location.getWorld() != world) {
      return;
    }
    var item = e.getEntity();
    var stack = item.getItemStack();
    if (stack.getType() != Material.LIGHT_WEIGHTED_PRESSURE_PLATE) {
      return;
    }
    if (x(56) <= location.getX() && location.getX() <= x(63) &&
      y(80) <= location.getY() && location.getY() <= y(91) &&
      z(56) <= location.getZ() && location.getZ() <= z(59)) {
      e.setCancelled(true);
    }
  }

  @EventHandler
  @SuppressWarnings("unused")
  public void onBlockPistonRetract(BlockPistonRetractEvent e) {
    var block = e.getBlock();
    var location = new Point3i(block.getLocation());
    if (pistonPositions.contains(location)) {
      var pressurePlatePos = location.added(0, 2, 0);
      Bukkit.getScheduler().runTaskLater(owner, () -> {
        Editor.Set(world, pressurePlatePos, Material.LIGHT_WEIGHTED_PRESSURE_PLATE);
      }, 10);
    }
  }

  private int x(int x) {
    return x + offset.x;
  }

  private int y(int y) {
    return y + offset.y;
  }

  private int z(int z) {
    return z + offset.z;
  }

  private Point3i pos(int x, int y, int z) {
    return new Point3i(x + offset.x, y + offset.y, z + offset.z);
  }
}
