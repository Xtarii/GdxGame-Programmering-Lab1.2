package com.gdx.game.networking;

import com.gdx.game.networking.message.NetworkMessage;
import com.gdx.game.networking.udp.UDPSocket;

import java.io.IOException;
import java.net.*;

/**
 * Network Socket
 * <p/>
 * Can be used as both a server and a client.
 */
public class Socket extends UDPSocket {
    /**
     * Socket Instance
     */
    private static Socket instance;





    /**
     * Update Thread
     */
    private final Thread updateThread;
    /**
     * Running Status
     */
    private boolean running = true;



    private Socket(int port) throws SocketException, UnknownHostException {
        super(port);
        updateThread = new Thread(update());
        updateThread.start();
        if(instance == null) instance = this;
    }
    private Socket(InetAddress address, int port) throws SocketException {
        super(address, port);
        updateThread = new Thread(update());
        if(instance == null) instance = this;
    }



    public void send(String data) throws IOException {
        byte[] byteMessage = (data + " --end").getBytes();
        DatagramPacket packet = new DatagramPacket(byteMessage, byteMessage.length, address, port);
        socket.send(packet);
    }
    public void send(NetworkClient client, String data) throws IOException {
        byte[] bytes = (data + " --end").getBytes();
        DatagramPacket packet = new DatagramPacket(bytes, bytes.length, client.getAddress(), port);
        socket.send(packet);
    }



    private Runnable update() {
        return () -> {
            while(running) {
                // Listen to Message
                try {
                    messageSystem.handleMessage(read(1024));
                } catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }



    private void connectToServer() throws IOException {
        // Connect to server
        NetworkMessage message = NetworkMessage.createEmpty(NetworkMessage.MessageType.CONNECTION_MESSAGE);

        new Thread(() -> {
            try {
                NetworkMessage res = read(1024);
                System.out.println(res);

            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
        send(message.toString());

        // Create Local Client
        updateThread.start();
    }



    @Override
    public void close() {
        running = false;
        super.close();
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
        Socket socket = new Socket(port);
        NetworkClient.setLocal(new NetworkClient(socket.address));
        return socket;
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
     * @throws IOException Socket Creation Error
     */
    public static Socket join(InetAddress address, int port) throws IOException {
        Socket socket = new Socket(address, port);
        socket.connectToServer();
        return socket;
    }



    /**
     * Gets Socket Instance
     *
     * @return Socket Instance
     * @throws SocketException No Socket found
     */
    public static Socket getInstance() throws SocketException {
        if(instance == null) throw new SocketException();
        return instance;
    }
}
