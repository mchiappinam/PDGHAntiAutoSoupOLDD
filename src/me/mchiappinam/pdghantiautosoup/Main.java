package me.mchiappinam.pdghantiautosoup;

import me.mchiappinam.pdghantiautosoup.EntityDamageByEntityListener;
import me.mchiappinam.pdghantiautosoup.EntityDamageListener;
import me.mchiappinam.pdghantiautosoup.InventoryClickListener;
import me.mchiappinam.pdghantiautosoup.Violation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static Main instance;
    private Map<UUID, Long> lastAttacked = new HashMap<>();
    public Map<UUID, Violation> violations = new HashMap<>();

    @Override
    public void onEnable() {
		File file=new File(getDataFolder(),"config.yml");
		if(!file.exists()) {
			try {
				saveResource("config_template.yml",false);
				File file2=new File(getDataFolder(),"config_template.yml");
				file2.renameTo(new File(getDataFolder(),"config.yml"));
			}catch(Exception e) {}
		}
		getServer().getConsoleSender().sendMessage("§3[PDGHAntiAutoSoup] §2ativando...");
        instance = this;

        PluginManager manager = Bukkit.getPluginManager();

        manager.registerEvents(new EntityDamageByEntityListener(), this);
        manager.registerEvents(new EntityDamageListener(), this);
        manager.registerEvents(new InventoryClickListener(), this);
		
		getServer().getConsoleSender().sendMessage("§3[PDGHAntiAutoSoup] §2ativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHAntiAutoSoup] §2Acesse: http://pdgh.com.br/");
    }

    @Override
    public void onDisable() {
		getServer().getConsoleSender().sendMessage("§3[PDGHAntiAutoSoup] §2desativando...");
        instance = null;
		getServer().getConsoleSender().sendMessage("§3[PDGHAntiAutoSoup] §2desativado - Plugin by: mchiappinam");
		getServer().getConsoleSender().sendMessage("§3[PDGHAntiAutoSoup] §2Acesse: http://pdgh.com.br/");
    }

    public static Main getInstance() {
        return instance;
    }

    public long getLastAttackTime(UUID uuid) {
        if (!lastAttacked.containsKey(uuid)) lastAttacked.put(uuid, System.currentTimeMillis());

        return lastAttacked.get(uuid);
    }

    public void setLastAttackTime(UUID uuid) {
        lastAttacked.put(uuid, System.currentTimeMillis());
    }

    public int raiseViolationLevel(UUID uuid) {
        Violation violation = getViolation(uuid);

        violation.raiseViolationLevel();
        violations.put(uuid, violation);

        return violation.getViolationLevel();
    }

    public Violation getViolation(UUID uuid) {
        if (!violations.containsKey(uuid)) violations.put(uuid, new Violation());

        return violations.get(uuid);
    }
}