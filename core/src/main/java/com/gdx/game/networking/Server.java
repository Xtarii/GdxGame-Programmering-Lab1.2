package com.gdx.game.networking;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Server extends DatagramSocket {
    public Server(int port) throws SocketException {
        super(port);
    }


    public String read() {
        byte[] byteMessage = new byte[65535];
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length);
        return packet.toString();
    }
}
