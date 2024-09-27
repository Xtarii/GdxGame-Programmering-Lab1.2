package com.gdx.game.networking;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Client {
    private final DatagramSocket server;
    private final InetAddress ip;
    private final int port;


    public Client(InetAddress ip, int port) throws SocketException {
        server = new DatagramSocket(port, ip);
        this.ip = ip;
        this.port = port;
    }


    public void send(String message) throws IOException {
        byte[] msg = message.getBytes();
        server.send(new DatagramPacket(msg, msg.length, ip, port));
    }
}
