package com.gdx.game.networking;

import java.net.InetAddress;
import java.net.SocketException;
import java.util.UUID;

/**
 * Client Object
 * <p>
 * Represents a client
 * connected to the game server.
 */
public class NetworkClient {
    /**
     * Client Address
     */
    private final InetAddress address;
    /**
     * Client UUID
     */
    private final UUID uuid;



    /**
     * Creates Client Object Instance
     *
     * @param address Client address
     * @param uuid Client UUID
     */
    public NetworkClient(InetAddress address, UUID uuid) {
        this.address = address;
        this.uuid = uuid;
    }

    /**
     * Creates Client Object Instance
     * <p>
     * Generates random UUID for
     * client identification.
     *
     * @param address Client address
     */
    public NetworkClient(InetAddress address) {
        this(address, UUID.randomUUID());
    }



    /**
     * Get Client Address
     *
     * @return Address
     */
    public InetAddress getAddress() { return address; }

    /**
     * Get Client UUID
     *
     * @return UUID
     */
    public UUID getUUID() { return uuid; }



    /**
     * Clint Verification Exception
     * <p/>
     * This gets thrown when a client can't be verified
     */
    public static class ClientVerifierExeption extends SocketException {}
}
