package com.gdx.game.networking.message;

public class ClientMessageSystem implements NetworkMessageSystem {
    @Override
    public void onConnection(NetworkMessage message) {
        System.out.println(message);
    }

    @Override
    public void onPosition(NetworkMessage message) {
        System.out.println(message);
    }
}
