package com.gdx.game.networking.message;

import java.net.InetAddress;

/**
 * Message Object
 */
public class NetworkMessage {
    /**
     * The address of the message sender
     */
    public final InetAddress sender;
    /**
     * Message
     */
    public final String message;



    /**
     * Message Object
     *
     * @param address Message Sender
     * @param message Message
     */
    public NetworkMessage(InetAddress address, String message) {
        sender = address;
        this.message = message;
    }



    @Override
    public String toString() {
        return (sender.getHostName() + " : " + message);
    }
}
