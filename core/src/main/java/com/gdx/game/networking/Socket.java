package com.gdx.game.networking;

import com.gdx.game.networking.udp.UDPSocket;

import java.io.IOException;
import java.net.*;

public class Socket extends UDPSocket {
    private Socket(int port) throws SocketException, UnknownHostException {
        super(port);
    }
    private Socket(InetAddress address, int port) throws SocketException {
        super(address, port);
    }



    public void send(String data) throws IOException {
        byte[] byteMessage = (data + " --end").getBytes();
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, address, port);
        socket.send(packet);
    }





    /**
     * Starts a Host Socket
     * <p/>
     * This will create a new Socket,
     * and the socket will be treated
     * as a host.
     *
     * @param port Hosting Port
     * @return Host Socket
     * @throws SocketException Socket Creation Error
     * @throws UnknownHostException Socket Host Error
     */
    public static Socket host(int port) throws SocketException, UnknownHostException {
        return new Socket(port);
    }
    /**
     * Joins a Host Socket
     * <p/>
     * This will create a new Socket,
     * and the socket will be treated
     * as a client.
     *
     * @param address Host Address
     * @param port Host Port
     * @return Client Socket
     * @throws SocketException Socket Creation Error
     */
    public static Socket join(InetAddress address, int port) throws SocketException {
        return new Socket(address, port);
    }
}
