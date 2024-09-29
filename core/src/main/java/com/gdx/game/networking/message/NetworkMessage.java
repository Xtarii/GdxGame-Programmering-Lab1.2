package com.gdx.game.networking.message;

import com.gdx.game.networking.NetworkClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Message Object
 */
public class NetworkMessage {
    /**
     * Message Type
     */
    public enum MessageType {
        /**
         * Position Message Type
         * <p/>
         * Messages of this type should
         * contain {@code position}.
         */
        POSITION_MESSAGE,
        /**
         * Animation Message Type
         * <p/>
         * Messages of this type should
         * contain {@code animation}.
         */
        ANIMATION_MESSAGE,

        /**
         * Connection Message Type
         * <p/>
         * Messages of this type represents
         * that the socket is trying to
         * connect to a server.
         */
        CONNECTION_MESSAGE // This is the last Message Type
    }

    /**
     * The address of the message sender
     */
    public final InetAddress sender;
    /**
     * Message
     */
    private final String message;
    /**
     * Message Parts
     */
    private final String[] parts;



    /**
     * Message Object
     *
     * @param address Message Sender
     * @param message Message
     */
    public NetworkMessage(InetAddress address, String message) {
        sender = address;
        this.message = message;
        parts = message.split(":");
    }



    @Override
    public String toString() { return message; }



    /**
     * Gets Message Type
     *
     * @return Message Type
     */
    public MessageType getMessageType() {
        for(String part : parts) {
            String[] miniParts = part.split("=");
            if(miniParts[0].strip().equals("type")) return MessageType.valueOf(miniParts[1].strip());
        }
        return null;
    }

    /**
     * Gets Message sender UUID
     *
     * @return Sender UUID
     */
    public UUID getMessageUUID() {
        for(String part : parts) {
            String[] miniParts = part.split("=");
            if(miniParts[0].strip().equals("uuid")) return UUID.fromString(miniParts[1].strip());
        }
        return null;
    }

    /**
     * Gets Message Value
     *
     * @param key Data key
     * @return Data value
     */
    public String get(String key) {
        for(String part : parts) {
            String[] miniParts = part.split("=");
            if(miniParts[0].strip().equals(key)) return miniParts[1].strip();
        }
        return null;
    }





    /**
     * Creates A Network Message Object
     * <p/>
     * Data arguments should be formated after
     * {@code <key>=<value>}
     *
     * @param type Message Type
     * @param args Message arguments
     * @return Network Message
     * @throws UnknownHostException Message Host Unknown Error
     */
    public static NetworkMessage create(MessageType type, String... args) throws UnknownHostException {
        String message = String.format("type=%s : uuid=%s", type, NetworkClient.getLocalInstance().getUUID());
        for(String arg : args) {
            message = message.concat(" : ").concat(arg);
        }
        return new NetworkMessage(InetAddress.getLocalHost(), message);
    }

    /**
     * Creates a network message object
     * <p/>
     * Data arguments should be formated after
     * {@code <key>=<value>}.
     * <p/>
     * The Created Message UUID is set to
     * custom and not the client UUID.
     * If the client UUID is wanted - consider
     * using {@link #create(MessageType, String...)}
     * 
     * @param type Message type
     * @param uuid Message UUID
     * @param args Message args
     * @return Network Message
     * @throws UnknownHostException Message Host Unknown Error
     */
    public static NetworkMessage create(MessageType type, UUID uuid, String... args) throws UnknownHostException {
        String message = String.format("type=%s : uuid=%s", type, uuid);
        for(String arg : args) {
            message = message.concat(" : ").concat(arg);
        }
        return new NetworkMessage(InetAddress.getLocalHost(), message);
    }
    /**
     * Creates an empty network message
     * <p/>
     * This message will not contain a UUID.
     *
     * @param type Message Type
     * @return Message
     * @throws UnknownHostException Message Host Unknown Error
     */
    public static NetworkMessage createEmpty(MessageType type) throws UnknownHostException {
        String message = String.format("type=%s", type);
        return new NetworkMessage(InetAddress.getLocalHost(), message);
    }
}
