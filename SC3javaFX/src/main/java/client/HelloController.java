package client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.io.IOException;

public class HelloController {
    private boolean nameTyped = false;
    @FXML
    private Label label;
    @FXML
    private TextField textField;
    @FXML
    private Button start;
    @FXML
    private Button enter;
    private Client client;

    @FXML
    protected void startClicked() throws IOException {
        start.setDisable(true);
        enter.setDisable(false);
        client = new Client(
                (String str) ->
                        Platform.runLater(new Runnable() {
                            public void run() {
                                label.setText(str);
                            }
                        })
        );
        try {
            client.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean isClicked = false;

    @FXML
    public void enterClicked() throws InterruptedException, IOException {

        if (!nameTyped) {
            client.send(textField.getText(), 0);
            nameTyped = true;
        } else {
            new Thread(() -> {
                try {
                    while (Integer.valueOf(textField.getText()).intValue() < 1 ||
                            Integer.valueOf(textField.getText()).intValue() > 5) {
                        label.setText("matches should be >0 & <6");
                    }
                    client.send(textField.getText(), 1);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }
        //textField.setText("");
        label.setText("Waiting second gamer");
    }
}