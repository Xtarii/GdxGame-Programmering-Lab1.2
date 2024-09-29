package com.gdx.game.networking.message;

/**
 * Network Message System
 * <p/>
 * Base interface for Network Message Handling Systems.
 */
public interface NetworkMessageSystem {
    /**
     * Handles Messages
     * <p/>
     * Main brain of the message system.
     * Handles the messages sent to this
     * {@link NetworkMessageSystem} and
     * handles them after {@code type}.
     *
     * @param message Network Message
     */
    default void handleMessage(NetworkMessage message) {
        // Handles Message Type
        switch(message.getMessageType()) {
            case CONNECTION_MESSAGE -> onConnection(message);
            case POSITION_MESSAGE -> onPosition(message);
        }
    }



    /**
     * Connection Message Handler
     * <p/>
     * Gets called when a connection
     * message is sent and handled
     * by the Message System.
     *
     * @param message Network Message
     */
    void onConnection(NetworkMessage message);

    /**
     * Position Message Handler
     * <p/>
     * Gets called when a position message
     * is sent and handled by the Message System.
     *
     * @param message Network Message
     */
    void onPosition(NetworkMessage message);
}
