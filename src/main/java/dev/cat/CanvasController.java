package dev.cat;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

public class CanvasController implements Initializable {

    @FXML
    public Canvas canvas;
    EventHandler<KeyEvent> keyPressListener = this::keyPressed;
    private final Map<KeyCode, AudioClip> keyToSound = new HashMap<>();



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawKey();
        createAudioClip();

        canvas.sceneProperty().addListener((observableValue,
                                              oldScene,
                                              newScene) -> {
            if (newScene != null) {
                newScene.addEventHandler(KeyEvent.KEY_PRESSED, keyPressListener);
            }
        });

    }

    private void keyPressed(KeyEvent keyEvent) {
        AudioClip sound = keyToSound.get(keyEvent.getCode());
        if(sound.isPlaying()) {
            return;
        }
        else {
            sound.play();
        }

    }

    private void createAudioClip() {
        String path = Objects.requireNonNull(
                        getClass().getResource("/C3.mp3"))
                .toExternalForm();
        AudioClip keySound = new AudioClip(path);
        keyToSound.put(KeyCode.A, keySound);
    }


    private void drawKey() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, 50, 70);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0, 0, 50, 70);
    }
}
