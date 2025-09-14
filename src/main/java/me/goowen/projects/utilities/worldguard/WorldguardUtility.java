package me.goowen.projects.utilities.worldguard;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;

import java.util.*;

@UtilityClass
public class WorldguardUtility {
    private static final WorldGuard worldGuardPlugin = WorldGuard.getInstance();;

    public Iterable<ProtectedRegion> getRegions(Location location) {
        return worldGuardPlugin.getPlatform().getRegionContainer().get(BukkitAdapter.adapt(location.getWorld())).getApplicableRegions(BlockVector3.at(location.getX(),location.getY(),location.getZ()));
    }

    public ProtectedRegion getRegion(Location location) {
        return getOptionalRegion(location).orElse(null);
    }

    public Optional<ProtectedRegion> getOptionalRegion(Location location) {
        Iterator<ProtectedRegion> regionIterator = getRegions(location).iterator();
        return regionIterator.hasNext() ? Optional.of(regionIterator.next()) : Optional.empty();
    }

    public ProtectedRegion getRegion(String id) {
        return getOptionalRegion(id).orElse(null);
    }

    public Optional<ProtectedRegion> getOptionalRegion(String id) {
        for (RegionManager regionManager : worldGuardPlugin.getPlatform().getRegionContainer().getLoaded()) {
            ProtectedRegion protectedRegion = regionManager.getRegion(id);
            if (protectedRegion != null)
                return Optional.of(protectedRegion);
        }
        return Optional.empty();
    }

    public static boolean isMember(ProtectedRegion region, OfflinePlayer target) {
        DefaultDomain currentMembers = region.getMembers();
        return currentMembers.contains(target.getUniqueId());
    }

    public static boolean isOwner(ProtectedRegion region, OfflinePlayer target) {
        DefaultDomain currentOwners = region.getOwners();
        return currentOwners.contains(target.getUniqueId());
    }

    public void addMember(ProtectedRegion region, OfflinePlayer newMember) {
        DefaultDomain currentMembers = region.getMembers();
        currentMembers.addPlayer(UUID.fromString(newMember.getUniqueId().toString()));
    }

    public void addOwner(ProtectedRegion region, OfflinePlayer newowner) {
        DefaultDomain currentOwners = region.getOwners();
        currentOwners.addPlayer(UUID.fromString(newowner.getUniqueId().toString()));
    }

    public void removeMember(ProtectedRegion region, OfflinePlayer oldMember) {
        DefaultDomain currentMembers = region.getMembers();
        currentMembers.removePlayer(UUID.fromString(oldMember.getUniqueId().toString()));
    }

    public void removeOwner(ProtectedRegion region, OfflinePlayer oldOwner) {
        DefaultDomain currentOwners = region.getOwners();
        currentOwners.removePlayer(UUID.fromString(oldOwner.getUniqueId().toString()));
    }

    public static ArrayList<UUID> getMembers(ProtectedRegion region) {
        DefaultDomain currentMembers = region.getMembers();
        return new ArrayList<>(currentMembers.getUniqueIds());
    }

    public static ArrayList<UUID> getOwners(ProtectedRegion region) {
        DefaultDomain currentOwners = region.getOwners();
        return new ArrayList<>(currentOwners.getUniqueIds());
    }
}