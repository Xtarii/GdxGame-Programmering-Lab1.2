package com.gdx.game.networking.message;

import com.gdx.game.networking.NetworkClient;
import com.gdx.game.networking.Socket;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServerMessageSystem implements NetworkMessageSystem {
    @Override
    public void onConnection(NetworkMessage message) {
//        if(message.get("uuid") != null) return; // Can't Respond to itself
        NetworkClient client = new NetworkClient(message.sender);

        try {
            Socket.getInstance().addClient(client);
            // Sends Client Data back
            NetworkMessage msg = NetworkMessage.create(NetworkMessage.MessageType.CONNECTION_MESSAGE, client.getUUID());
            Socket.getInstance().send(client, msg.toString());

        }catch(SocketException e) {
            throw new RuntimeException(e);
        } catch(UnknownHostException e) {
            throw new RuntimeException(e);
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onPosition(NetworkMessage message) {
        System.out.println(message);
    }
}
