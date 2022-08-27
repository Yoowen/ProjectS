package me.goowen.projectm.utilities;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Polygonal2DRegion;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.managers.storage.StorageException;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedPolygonalRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

public class WGUtil {
    /**
     * WorldGuard class to make the usage of WorldGuard easy. This is the 1.13.x version! <i>Note that if you do
     * use this in one of your projects, leave this notice.</i> <i>Please do credit
     * me if you do use this in one of your projects.</i>
     *
     * @author SBDeveloper [Fixed 1.13.x support]
     */

    public static WorldGuardPlugin wgp = null;
    public static WorldEditPlugin wep = null;

    /**
     * Check if WorldGuard is connected to the Util
     *
     * @return boolean
     */
    public static boolean hasWorldGuard() {
        return wgp != null;
    }

    /**
     * Check if WorldEdit is connected to the Util
     *
     * @return boolean
     */
    public static boolean hasWorldEdit() {
        return wep != null;
    }

    /**
     * Connect WorldGuard with the Util [Main class]
     *
     * @param plugin The WorldGuard plugin
     */
    public static void setWorldGuard(Plugin plugin) {
        wgp = (WorldGuardPlugin) plugin;
    }

    /**
     * Connect WorldEdit with the Util [Main class]
     *
     * @param plugin The WorldEdit plugin
     * @return boolean
     */
    public static boolean setWorldEdit(Plugin plugin) {
        wep = (WorldEditPlugin) plugin;
        return true;
    }

    /**
     * Create an empty region [No flags/members/priority] from a selection
     *
     * @param p  The player that wants to create a region
     * @param id The id of the region
     * @throws StorageException
     */
    public static void createRegion(Player p, String id) throws StorageException, IncompleteRegionException {
        LocalSession l = WorldEdit.getInstance().getSessionManager().get(BukkitAdapter.adapt(p));
        Region s = l.getWorldSelection();
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(p.getWorld()));
        if (rm != null) {
            rm.removeRegion(id);
        }
        ProtectedRegion region;
        // Detect the type of region from WorldEdit
        if (s instanceof Polygonal2DRegion) {
            Polygonal2DRegion polySel = (Polygonal2DRegion) s;
            int minY = polySel.getMinimumY();
            int maxY = polySel.getMaximumY();
            region = new ProtectedPolygonalRegion(id, polySel.getPoints(), minY, maxY);
        } else { /// default everything to cuboid
            BlockVector3 min = BlockVector3.at(s.getMinimumPoint().getX(), s.getMinimumPoint().getY(), s.getMinimumPoint().getZ());
            BlockVector3 max = BlockVector3.at(s.getMaximumPoint().getX(), s.getMaximumPoint().getY(), s.getMaximumPoint().getZ());
            region = new ProtectedCuboidRegion(id, min, max);
        }
        region.setPriority(1);
        region.setFlag(Flags.INTERACT, StateFlag.State.DENY);
        region.setFlag(Flags.INTERACT.getRegionGroupFlag(), RegionGroup.NON_MEMBERS);
        rm.addRegion(region);
        rm.save();
    }

    /**
     * Get the regions an location is in
     *
     * @param loc The location
     * @return An ArrayList with ProtectedRegions
     */
    public static ArrayList<ProtectedRegion> getRegionsIn(Location loc) {
        ArrayList<ProtectedRegion> inRegions = new ArrayList<>();

        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld()));
        if (rm != null) {
            for (ProtectedRegion protectedRegion : rm.getApplicableRegions(BukkitAdapter.asBlockVector(loc)))
                inRegions.add(protectedRegion);

            return inRegions;
        }
        return null;
    }

    /**
     * Get an ProtectedRegion from an region id [in any world]
     *
     * @param str The Region ID
     * @param loc The location
     * @return An ProtectedRegion
     */
    public static ProtectedRegion getRegionfromString(String str, Location loc) {
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld()));
        if (rm != null) {
            ApplicableRegionSet mogreg = rm.getApplicableRegions(BukkitAdapter.asBlockVector(loc));
            for (ProtectedRegion mogregion : mogreg) {
                if (str.equalsIgnoreCase(mogregion.getId())) {
                    return mogregion;
                }
            }
        }
        //Niks gevonden!
        return null;
    }

    /**
     * Get an ProtectedRegion from an region id [in the world of the location]
     *
     * @param str The Region ID
     * @param loc The location
     * @return An ProtectedRegion
     */
    public static ProtectedRegion getRegionfromStringInWorld(String str, Location loc) {
        String strgood = str.toLowerCase();
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(loc.getWorld()));
        Map<String, ProtectedRegion> mp = rm.getRegions();
        ProtectedRegion reg = mp.get(strgood);
        //noinspection RedundantIfStatement
        if (reg != null) {
            return reg;
        } else {
            return null;
        }
    }

    /**
     * Check if a player is member of an region
     *
     * @param w      The world
     * @param region The region
     * @param target The player you want to check
     * @return boolean
     */
    public static boolean isMember(World w, ProtectedRegion region, OfflinePlayer target) {
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

        ProtectedRegion currentRegion = rm.getRegion(region.getId());
        DefaultDomain currentMembers = currentRegion.getMembers();
        return currentMembers.contains(target.getUniqueId());
    }

    /**
     * Check if a player is owner of an region
     *
     * @param w      The world
     * @param region The region
     * @param target The player you want to check
     * @return boolean
     */
    public static boolean isOwner(World w, ProtectedRegion region, OfflinePlayer target) {
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

        ProtectedRegion currentRegion = rm.getRegion(region.getId());
        DefaultDomain currentOwners = currentRegion.getOwners();
        return currentOwners.contains(target.getUniqueId());
    }

    /**
     * Add a player as a member in an region
     *
     * @param w         The world
     * @param region    The region
     * @param newmember The player you want to add
     * @return boolean
     */
    public static boolean addMember(World w, ProtectedRegion region, OfflinePlayer newmember) {
        try {
            RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

            ProtectedRegion currentRegion = rm.getRegion(region.getId());
            DefaultDomain currentMembers = currentRegion.getMembers();
            currentMembers.addPlayer(UUID.fromString(newmember.getUniqueId().toString()));

            rm.save();

            return true;
        } catch (StorageException e) {
            return false;
        }
    }

    /**
     * Add a player as a owner in an region
     *
     * @param w        The world
     * @param region   The region
     * @param newowner The player you want to add
     * @return boolean
     */
    public static boolean addOwner(World w, ProtectedRegion region, OfflinePlayer newowner) {
        try {
            RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

            ProtectedRegion currentRegion = rm.getRegion(region.getId());
            DefaultDomain currentOwners = currentRegion.getOwners();
            currentOwners.addPlayer(UUID.fromString(newowner.getUniqueId().toString()));

            rm.save();

            return true;
        } catch (StorageException e) {
            return false;
        }
    }

    /**
     * Remove a player from an region as a member
     *
     * @param w         The world
     * @param region    The region
     * @param newmember The player you want to remove
     * @return boolean
     */
    public static boolean removeMember(World w, ProtectedRegion region, OfflinePlayer newmember) {
        try {
            RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

            ProtectedRegion currentRegion = rm.getRegion(region.getId());
            DefaultDomain currentMembers = currentRegion.getMembers();
            currentMembers.removePlayer(UUID.fromString(newmember.getUniqueId().toString()));

            rm.save();

            return true;
        } catch (StorageException e) {
            return false;
        }
    }

    /**
     * Remove a player from an region as a owner
     *
     * @param w        The world
     * @param region   The region
     * @param newowner The player you want to remove
     * @return boolean
     */
    public static boolean removeOwner(World w, ProtectedRegion region, UUID newowner) {
        try {
            RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

            ProtectedRegion currentRegion = rm.getRegion(region.getId());
            DefaultDomain currentOwners = currentRegion.getOwners();
            currentOwners.removePlayer(newowner);

            rm.save();

            return true;
        } catch (StorageException e) {
            return false;
        }
    }

    /**
     * Get the members from an region
     *
     * @param w      The world
     * @param region The region
     * @return Arraylist of members by UUID
     */
    public static ArrayList<UUID> getMembers(World w, ProtectedRegion region) {
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

        ProtectedRegion currentRegion = rm.getRegion(region.getId());
        DefaultDomain currentMembers = currentRegion.getMembers();

        ArrayList<UUID> members = new ArrayList<>();
        members.addAll(currentMembers.getUniqueIds());
        return members;
    }

    /**
     * Get the owners from an region
     *
     * @param region The region
     * @return Arraylist of members by UUID
     */
    public static ArrayList<UUID> getOwners(Player p, ProtectedRegion region) {
        World world = p.getWorld();
        RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(world));

        ProtectedRegion currentRegion = rm.getRegion(region.getId());
        DefaultDomain currentOwners = currentRegion.getOwners();

        ArrayList<UUID> owners = new ArrayList<>();
        owners.addAll(currentOwners.getUniqueIds());
        return owners;
    }

    /**
     * Remove an region
     *
     * @param w    The world
     * @param name The regionname
     * @return boolean
     */
    public static boolean removeRegion(World w, String name) {
        try {
            RegionManager rm = WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(w));

            ProtectedRegion currentRegion = rm.getRegion(name);
            if (currentRegion == null) {
                return false;
            }

            rm.removeRegion(name);
            rm.save();

            return true;
        } catch (StorageException e) {
            return false;
        }
    }
}