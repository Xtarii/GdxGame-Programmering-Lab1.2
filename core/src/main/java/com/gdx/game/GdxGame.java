package com.gdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.gdx.game.manager.PreferenceManager;
import com.gdx.game.manager.ResourceManager;
import com.gdx.game.networking.NetworkClient;
import com.gdx.game.networking.message.NetworkMessage;
import com.gdx.game.networking.Socket;
import com.gdx.game.screen.CharacterSelectionScreen;
import com.gdx.game.screen.GameScreen;
import com.gdx.game.screen.MenuScreen;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class GdxGame extends Game {
	private SpriteBatch batch;
	private ResourceManager resourceManager;
	private PreferenceManager preferenceManager = new PreferenceManager();
	private MenuScreen menuScreen;
	private CharacterSelectionScreen characterSelectionScreen;
	private GameScreen gameScreen;

	public SpriteBatch getBatch() {
		return batch;
	}

	public MenuScreen getMenuScreen() {
		return menuScreen;
	}

	public CharacterSelectionScreen getCharacterSelectionScreen() {
		return characterSelectionScreen;
	}

	public GameScreen getGameScreen() {
		return gameScreen;
	}

	public void setGameScreen(GameScreen gameScreen) {
		this.gameScreen = gameScreen;
	}

	public PreferenceManager getPreferenceManager() {
		return preferenceManager;
	}

	public void create() {
		batch = new SpriteBatch();
		resourceManager = new ResourceManager();

		menuScreen = new MenuScreen(this, resourceManager);
		characterSelectionScreen = new CharacterSelectionScreen(this, resourceManager);

		this.setScreen(menuScreen);





		/// DEBUG
		try {
			int port = 8080;
			Socket server = Socket.host(port);

		} catch(SocketException e) {
            throw new RuntimeException(e);
        } catch(UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

	@Override
	public void dispose() {
		try{
			Socket.getInstance().close();
		} catch(SocketException e) {
            throw new RuntimeException(e);
        }

        super.dispose();
		batch.dispose();
		menuScreen.dispose();
		characterSelectionScreen.dispose();
		gameScreen.dispose();
		resourceManager.dispose();
	}
}
