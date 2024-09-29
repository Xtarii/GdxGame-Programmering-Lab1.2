package com.gdx.game.networking;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * Client Object
 * <p/>
 * Represents a client
 * connected to the game server.
 */
public class NetworkClient {
    /**
     * Local Client Instance
     */
    private static NetworkClient local;



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
     * <p/>
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
     * Gets the Local Client Instance
     *
     * @return Local Client
     * @throws UnknownHostException Host Error
     */
    public static NetworkClient getLocalInstance() throws UnknownHostException {
        if(local != null) return local;
        local = new NetworkClient(InetAddress.getLocalHost());
        return local;
    }

    /**
     * Sets Local Client Instance
     *
     * @param client Client Instance
     * @throws ClientVerifierExeption Gets Thrown if local client is set
     */
    public static void setLocal(NetworkClient client) throws ClientVerifierExeption {
        if(local != null) throw new ClientVerifierExeption();
        local = client;
    }



    /**
     * Clint Verification Exception
     * <p/>
     * This gets thrown when a client can't be verified
     */
    public static class ClientVerifierExeption extends SocketException {}
}
