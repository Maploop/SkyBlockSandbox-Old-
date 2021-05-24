package net.maploop.items.api;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedBlockData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import net.maploop.items.Items;
import net.maploop.items.protocol.WrapperPlayClientUpdateSign;
import net.maploop.items.protocol.WrapperPlayServerBlockChange;
import net.maploop.items.protocol.WrapperPlayServerOpenSignEntity;
import net.maploop.items.protocol.WrapperPlayServerUpdateSign;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Hashtable;
import java.util.UUID;

public class SignGUIRevamped {
    private static Hashtable<UUID, BlockPosition> playerBlockPositions;

    /**
     * Simplest method I could find to open a SignGUI to a player
     * and get their input afterwards.
     *
     * @param player The player which the sign gui opens to.
     * @param text The text that the gui will have when opened.
     * @param handler Handles the event when the player closes the sign.
     */
    public static void openSignEditor(Player player, String[] text, SignGUIUpdateHandler handler) {
        playerBlockPositions = new Hashtable<>();
        registerSignUpdateListener(handler);

        int x = player.getLocation().getBlockX();
        int y = 255;
        int z = player.getLocation().getBlockZ();
        com.comphenix.protocol.wrappers.BlockPosition bp = new com.comphenix.protocol.wrappers.BlockPosition(x, y, z);

        WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange();
        WrappedBlockData blockData = WrappedBlockData.createData(Material.SIGN_POST);
        blockChangePacket.setBlockData(blockData);
        blockChangePacket.setLocation(bp);
        blockChangePacket.sendPacket(player);

        WrapperPlayServerUpdateSign updateSignPacket = new WrapperPlayServerUpdateSign();
        updateSignPacket.setLocation(new BlockPosition(x, y, z));
        WrappedChatComponent[] lines = new WrappedChatComponent[4];
        lines[0] = WrappedChatComponent.fromText(text[0]);
        lines[1] = WrappedChatComponent.fromText(text[1]);
        lines[2] = WrappedChatComponent.fromText(text[2]);
        lines[3] = WrappedChatComponent.fromText(text[3]);
        updateSignPacket.setLines(lines);

        updateSignPacket.sendPacket(player);

        WrapperPlayServerOpenSignEntity packet = new WrapperPlayServerOpenSignEntity();
        packet.setLocation(new BlockPosition(x, y, z));
        packet.sendPacket(player);

        playerBlockPositions.put(player.getUniqueId(), bp);
    }

    private static void registerSignUpdateListener(SignGUIUpdateHandler handler) {
        ProtocolManager manager = ProtocolLibrary.getProtocolManager();

        if(playerBlockPositions == null) {
            playerBlockPositions = new Hashtable<>();
        }
        manager.addPacketListener(new PacketAdapter(Items.getInstance(), PacketType.Play.Client.UPDATE_SIGN){
            @Override
            public void onPacketReceiving(PacketEvent event) {
                String[] text = new String[4];
                Player player = event.getPlayer();
                WrapperPlayClientUpdateSign packet = new WrapperPlayClientUpdateSign(event.getPacket());
                com.comphenix.protocol.wrappers.BlockPosition bp = packet.getLocation();

                BlockPosition playerBlockPos = playerBlockPositions.get(player.getUniqueId());
                if(playerBlockPos != null) {
                    if(bp.getX() == playerBlockPos.getX() && bp.getY() == playerBlockPos.getY() && bp.getZ() == playerBlockPos.getZ()) {

                        for(int i = 0; i < packet.getLines().length; i++) {
                            WrappedChatComponent chat = packet.getLines()[i];
                            String str = StringEscapeUtils.unescapeJavaScript(chat.getJson());
                            str = str.substring(1, str.length()-1);
                            text[i] = str;
                        }

                        WrapperPlayServerBlockChange blockChangePacket = new WrapperPlayServerBlockChange();
                        WrappedBlockData blockData = WrappedBlockData.createData(Material.AIR);
                        blockChangePacket.setBlockData(blockData);
                        blockChangePacket.setLocation(playerBlockPos);
                        blockChangePacket.sendPacket(player);

                        playerBlockPositions.remove(player.getUniqueId());

                        //trigger the sign update event
                        SignGUIUpdateEvent updateEvent = new SignGUIUpdateEvent(player, text);
                        Bukkit.getServer().getPluginManager().callEvent(updateEvent);
                        handler.onUpdate(updateEvent);
                    }
                }
            }

            @Override
            public void onPacketSending(PacketEvent event) {

            }
        });
    }

    public interface SignGUIUpdateHandler {
        void onUpdate(SignGUIUpdateEvent event);
    }
}
