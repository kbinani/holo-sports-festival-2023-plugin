package com.github.kbinani.holosportsfestival2023.himerace.stage.fight;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.PI;

class DefenceSphere {
  private final @Nonnull List<ParticleRing> rings;
  private final @Nonnull BukkitTask timer;

  DefenceSphere(@Nonnull JavaPlugin owner, @Nonnull Location center) {
    this.rings = Arrays.stream(new ParticleRing[]{
      new ParticleRing(
        owner, center,
        new Vector(0.34, 0.642, -0.687), new Vector(0.34, 0.642, -0.687).rotateAroundAxis(new Vector(1, 0, 0), PI * 0.5),
        NamedTextColor.BLUE, 1.47
      ),
      new ParticleRing(
        owner, center,
        new Vector(0.407, -0.330, -0.852), new Vector(0.407, -0.330, -0.852).rotateAroundAxis(new Vector(0, 1, 0), PI * 0.5),
        NamedTextColor.BLUE, -1.57
      ),
      new ParticleRing(
        owner, center,
        new Vector(0.309, -0.907, 0.287), new Vector(0.309, -0.907, 0.287).rotateAroundAxis(new Vector(0, 0, 1), PI * 0.5),
        NamedTextColor.BLUE, 1.67
      ),
    }).collect(Collectors.toList());
    this.timer = Bukkit.getScheduler().runTaskTimer(owner, this::tick, 0, 1);
  }

  void dispose() {
    rings.clear();
    timer.cancel();
  }

  private void tick() {
    rings.forEach(ParticleRing::tick);
  }
}
