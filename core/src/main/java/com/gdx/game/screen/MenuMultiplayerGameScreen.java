package com.gdx.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.gdx.game.GdxGame;
import com.gdx.game.manager.ResourceManager;
import com.gdx.game.networking.Socket;
import com.gdx.game.profile.ProfileManager;
import com.gdx.game.screen.transition.effects.FadeOutTransitionEffect;
import com.gdx.game.screen.transition.effects.TransitionEffect;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import static com.gdx.game.audio.AudioObserver.AudioTypeEvent.MENU_THEME;

/**
 * Multiplayer Menu Screen
 */
public class MenuMultiplayerGameScreen extends BaseScreen {

    // Server Hosting Port
    private final int port = 8080;

    private final Table newTable;
    private final Table topTable;
    private final Table bottomTable;
    private final Stage newStage = new Stage();

    private final TextField profileText;
    private final TextField hostText;

    private Dialog overwriteDialog;
    private final BaseScreen previousScreen;
    private float stateTime;



    // Creates Multiplayer Menu
    public MenuMultiplayerGameScreen(GdxGame gdxGame, BaseScreen previousScreen, ResourceManager resourceManager) {
        super(gdxGame, resourceManager);
        this.previousScreen = previousScreen;
        super.musicTheme = MENU_THEME;

        resourceManager.setMenuNewGameScreen(true);

        // Host Text
        Label hostName = new Label("Enter Host IP: ", ResourceManager.skin);
        hostText = new TextField("10.10.10.10:8080", ResourceManager.skin);
        hostText.setMaxLength(21);
        // Profile Text
        Label profileName = new Label("Enter Profile Name: ", ResourceManager.skin);
        profileText = new TextField("default", ResourceManager.skin);
        profileText.setMaxLength(20);


        newTable = createTable();

        topTable = createTable();
        topTable.setFillParent(true);

        // Adds Labels and Text to Screen
        topTable.add(hostName).center();
        topTable.add(hostText).center();
        topTable.add(profileName).center();
        topTable.add(profileText).center();

        // Bottom Table
        bottomTable = createTable();
        bottomTable.setWidth(Gdx.graphics.getWidth());
        bottomTable.setHeight(Gdx.graphics.getHeight()/2f);
        bottomTable.center();

        createOverwriteDialog();

        // Multiplayer Buttons
        handleHostButton();
        handleJoinButton();

        handleOverwriteButton();
        handleCancelButton();
    }



    // Overwrite Dialog
    private void createOverwriteDialog() {
        overwriteDialog = new Dialog("Overwrite?", ResourceManager.skin);
        Label overwriteLabel = new Label("Overwrite existing profile name?", ResourceManager.skin);

        overwriteDialog.setKeepWithinStage(true);
        overwriteDialog.setModal(true);
        overwriteDialog.setMovable(false);
        overwriteDialog.text(overwriteLabel);
        overwriteDialog.row();
    }



    // Host Button, Host new Multiplayer Game
    private void handleHostButton() {
        createButton("Host", 0, newTable.getHeight()/9, newTable);
        Actor hostButton = newTable.getCells().get(0).getActor();
        bottomTable.add(hostButton).padRight(50);
        hostButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try{
                    // Hosting Server
                    Socket.host(port);

                    //check to see if the current profile matches one that already exists
                    boolean exists = ProfileManager.getInstance().doesProfileExist(hostText.getText());
                    if(exists) {
                        //Pop up dialog for Overwrite
                        overwriteDialog.show(newStage);
                    }else {
                        // Creates Game Instance
                        ProfileManager.getInstance().writeProfileToStorage(hostText.getText(), "", false);
                        ProfileManager.getInstance().setCurrentProfile(hostText.getText());
                        ProfileManager.getInstance().setIsNewProfile(true);

                        ArrayList<TransitionEffect> effects = new ArrayList<>();
                        effects.add(new FadeOutTransitionEffect(1f));
                        //effects.add(new FadeInTransitionEffect(1f)); TODO: Issue with fadein effect
                        setScreenWithTransition((BaseScreen) gdxGame.getScreen(), gdxGame.getCharacterSelectionScreen(), effects);
                    }

                }catch(SocketException e) {
                    try {
                        Socket.closeInstance();
                    } catch(SocketException ex) { throw new RuntimeException(ex); }
                    throw new RuntimeException(e);
                }catch(UnknownHostException e) {
                    try {
                        Socket.closeInstance();
                    } catch(SocketException ex) { throw new RuntimeException(ex); }
                    throw new RuntimeException(e);
                }
            }
        });
    }
    // Join Button, Joins multiplayer server
    private void handleJoinButton() {
        createButton("Join", 0, newTable.getHeight()/9, newTable);
        Actor joinButton = newTable.getCells().get(1).getActor();
        bottomTable.add(joinButton);
        joinButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try{
                    // Gets Server Props
                    String[] parts = hostText.getText().split(":");
                    InetAddress address = InetAddress.getByName(parts[0].strip());
                    int port = Integer.parseInt(parts[1].strip());
                    // Joins Server
                    Socket.join(address, port);


                    //check to see if the current profile matches one that already exists
                    boolean exists = ProfileManager.getInstance().doesProfileExist(hostText.getText());
                    if(exists) {
                        //Pop up dialog for Overwrite
                        overwriteDialog.show(newStage);
                    }else {
                        // Creates Game Instance
                        ProfileManager.getInstance().writeProfileToStorage(hostText.getText(), "", false);
                        ProfileManager.getInstance().setCurrentProfile(hostText.getText());
                        ProfileManager.getInstance().setIsNewProfile(true);

                        ArrayList<TransitionEffect> effects = new ArrayList<>();
                        effects.add(new FadeOutTransitionEffect(1f));
                        //effects.add(new FadeInTransitionEffect(1f)); TODO: Issue with fadein effect
                        setScreenWithTransition((BaseScreen) gdxGame.getScreen(), gdxGame.getCharacterSelectionScreen(), effects);
                    }

                }catch(SocketException e) {
                    try {
                        Socket.closeInstance();
                    } catch(SocketException ex) { throw new RuntimeException(ex); }
                    throw new RuntimeException(e);
                }catch(UnknownHostException e) {
                    try {
                        Socket.closeInstance();
                    } catch(SocketException ex) { throw new RuntimeException(ex); }
                    throw new RuntimeException(e);
                } catch(IOException e) {
                    try {
                        Socket.closeInstance();
                    } catch(SocketException ex) { throw new RuntimeException(ex); }
                    throw new RuntimeException(e);
                }
            }
        });
    }



    // Game Overwrite
    private void handleOverwriteButton() {
        createButton("Overwrite",0, newTable.getHeight()/5, newTable);

        Actor overwriteButton = newTable.getCells().get(2).getActor();
        overwriteDialog.button((Button) overwriteButton).bottom().left();
        overwriteButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                String messageText = profileText.getText();
                ProfileManager.getInstance().writeProfileToStorage(messageText, "", true);
                ProfileManager.getInstance().setCurrentProfile(messageText);
                ProfileManager.getInstance().setIsNewProfile(true);
                overwriteDialog.hide();

                ArrayList<TransitionEffect> effects = new ArrayList<>();
                effects.add(new FadeOutTransitionEffect(1f));
                //effects.add(new FadeInTransitionEffect(1f)); TODO: Issue with fadein effect
                setScreenWithTransition((BaseScreen) gdxGame.getScreen(), gdxGame.getCharacterSelectionScreen(), effects);
            }

        });
    }
    // Game Overwrite cancel
    private void handleCancelButton() {
        createButton("Cancel",0, newTable.getHeight()/5, newTable);

        Actor cancelButton = newTable.getCells().get(3).getActor();
        overwriteDialog.button((Button) cancelButton).bottom().right();
        cancelButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                overwriteDialog.hide();
            }
        });
    }

    @Override
    public void show() {
        newStage.addActor(newTable);
        newStage.addActor(topTable);
        newStage.addActor(bottomTable);
        Gdx.input.setInputProcessor(newStage);
    }

    @Override
    public void render(float delta) {
        stateTime += Gdx.graphics.getDeltaTime();

        if (previousScreen != null) {
            previousScreen.render(stateTime);
        }

        show();
        newStage.act(delta);
        newStage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        newTable.remove();
    }

    @Override
    public void hide() {
        resourceManager.setMenuNewGameScreen(false);
    }
}
