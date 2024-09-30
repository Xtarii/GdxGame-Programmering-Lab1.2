package com.gdx.game.networking.udp;

import com.gdx.game.networking.NetworkClient;
import com.gdx.game.networking.message.ClientMessageSystem;
import com.gdx.game.networking.message.NetworkMessage;
import com.gdx.game.networking.message.NetworkMessageSystem;
import com.gdx.game.networking.message.ServerMessageSystem;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * UDP Socket Server Class
 */
public class UDPSocket {
    /**
     * Server Clients List
     * <p/>
     * Stores all clients connected
     * to this server instance.
     * <p/>
     * Note, that if this {@link UDPSocket} is used
     * as a client socket, this list will not
     * contain any clients.
     */
    private final ArrayList<NetworkClient> clients = new ArrayList<>();
    /**
     * Socket Instance
     */
    protected final DatagramSocket socket;
    /**
     * Socket Address
     */
    public final InetAddress address;
    /**
     * Socket Port
     */
    public final int port;
    /**
     * Socket Role
     */
    public final boolean isHost;
    /**
     * Socket Message System
     */
    public final NetworkMessageSystem messageSystem;



    /**
     * Creates a UDP Socket Instance
     *
     * @param port UDP Port
     * @throws SocketException UDP Creation Error
     * @throws UnknownHostException Host Error
     */
    public UDPSocket(int port) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port);
        address = InetAddress.getLocalHost();
        this.port = port;
        isHost = true;

        // Message System
        messageSystem = new ServerMessageSystem();
    }
    /**
     * Creates a UDP Socket Instance
     *
     * @param address UDP Address
     * @param port UDP Port
     * @throws SocketException UDP Creation Error
     */
    public UDPSocket(InetAddress address, int port) throws SocketException {
        socket = new DatagramSocket(port);
        this.address = address;
        this.port = port;
        isHost = false;

        // Message System
        messageSystem = new ClientMessageSystem();
    }



    /**
     * Reads raw UDP Package
     * <p/>
     * Reads package with specified byte size.
     *
     * @param size Package Size
     * @return UDP Package
     * @throws IOException IO Package reading error
     */
    public DatagramPacket readRawUDPPackage(int size) throws IOException {
        byte[] bytes = new byte[size];
        DatagramPacket dataPackage = new DatagramPacket(bytes, bytes.length);
        socket.receive(dataPackage);
        return dataPackage;
    }
    /**
     * Reads raw UDP Package
     * <p/>
     * Reads package with default byte size
     * of {@code 65535}. If the package is
     * known to being bigger or smaller
     * consider using {@link #readRawUDPPackage(int)}.
     *
     * @return UDP Package
     * @throws IOException IO Package reading error
     */
    public DatagramPacket readRawUDPPackage() throws IOException {
        return readRawUDPPackage(65535);
    }



    /**
     * Reads Network Message from Socket
     * <p/>
     * Reads Message sent to this socket,
     * and auto removes the {@code --end}
     * from the message.
     * If you need the hole message consider
     * using {@link #readRawUDPPackage()}.
     *
     * @param size Message Size
     * @return Network Message
     * @throws IOException IO Message Error
     */
    public NetworkMessage read(int size) throws IOException {
        DatagramPacket packet = readRawUDPPackage(size);
        String message = new String(packet.getData()).split("--end")[0].strip();
        return new NetworkMessage(packet.getAddress(), message);
    }


    /**
     * Adds Network Client to Socket Client List
     * <p/>
     * Adds Client to Sockets client list if this
     * socket is a server instance.
     *
     * @param client Network Client
     * @throws NetworkClient.ClientVerifierExeption Client Verification Error
     * @throws SocketRoleException Server Socket Error
     */
    public void addClient(NetworkClient client) throws NetworkClient.ClientVerifierExeption, SocketRoleException {
        // Verifies Client and this Socket
        if(!isHost) throw new SocketRoleException();
        for(NetworkClient c : clients)
            if(c.getUUID().equals(client.getUUID())) throw new NetworkClient.ClientVerifierExeption();
        clients.add(client); // Adds Client to Clients List ( This should be handled carefully )
    }
    /**
     * Removes Client from Sockets Client List
     * <p/>
     * Throws Error if this socket is not a
     * server socket.
     *
     * @param client Client
     * @throws SocketRoleException Server Socket Error
     */
    public void removeClient(NetworkClient client) throws SocketRoleException {
        if(!isHost) throw new SocketRoleException();
        clients.remove(client);
    }



    /**
     * Socket Disposal
     * <p/>
     * Closes socket instances and
     * cleans up network objects.
     */
    public void close() {
        // Close Clients

        socket.close();
    }



    /**
     * Socket Role Exception
     * <p/>
     * This gets thrown when the socket is treated
     * as something it is not created for.
     */
    public static class SocketRoleException extends SocketException {}
}
