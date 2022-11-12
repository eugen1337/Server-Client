package com.example.progressbar;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client {
    private ProgressBar progress;
    private InputStream in;
    private OutputStream out;
    private double value;

    private PaintThread paint;
    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
    public Client(ProgressBar progress) throws IOException {
        Socket s = new Socket("127.0.0.1",8000);
        in = s.getInputStream();
        out = s.getOutputStream();
        this.progress = progress;
        paint = new PaintThread();
        paint.start();
    }
    public void sendAction(int action) throws IOException {
        out.write(action);
    }
    public int receiveAction() throws IOException {
        int a = in.read();
        return a;
    }

    private class PaintThread extends Thread{
        @Override
        public void run() {
            while (true) {
                try
                {
                    byte[] b = new byte[8];
                    in.read(b);
                    value = toDouble(b);
                    System.out.println(value);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Platform.runLater(new Runnable() {
                    public void run() {
                        progress.setProgress(value);
                    }
                });
            }
        }
    }
}
