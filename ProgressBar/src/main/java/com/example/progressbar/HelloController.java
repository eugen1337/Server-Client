package com.example.progressbar;

import Server.Model;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.io.IOException;

public class HelloController {
    @FXML
    private ProgressBar progress;
    @FXML
    private Button pauseButton;
    @FXML
    private Button startButton;
    @FXML
    private Button stopButton;
    private boolean isPaused = false;
    private Client client;

    @FXML
    public void initialize() throws IOException {
        try {
             client = new Client((double percents) -> {
                Platform.runLater(new Runnable() {
                    public void run() {
                        progress.setProgress(percents);
                    }
                });

            });
        }
        catch (IOException e){
            System.out.println("Client error");
        }
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
    }
    @FXML
    protected void startClicked() throws IOException {
        client.sendAction(1);
        startButton.setText("Перезапуск");
        pauseButton.setDisable(false);
        stopButton.setDisable(false);

    }
    @FXML
    protected void stopClicked() throws IOException {
        client.sendAction(2);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
    }
    @FXML
    protected void pauseClicked() throws IOException {
        client.sendAction(3);
        if (isPaused) {
            isPaused = !isPaused;
            pauseButton.setText("Пауза");
            stopButton.setDisable(false);
            startButton.setDisable(false);

        }
        else {
            isPaused = !isPaused;
            pauseButton.setText("Продолжить");
            startButton.setDisable(true);
            stopButton.setDisable(true);
        }
    }
}