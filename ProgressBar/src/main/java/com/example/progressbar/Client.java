package com.example.progressbar;

import javafx.application.Platform;
import javafx.scene.control.ProgressBar;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.ByteBuffer;

public class Client {
    private InputStream in;
    private OutputStream out;
    private double value;
    private PaintThread paint;
    Updatable updater;

    public Client(Updatable updater) throws IOException {
        Socket s = new Socket("127.0.0.1",8000);
        in = s.getInputStream();
        out = s.getOutputStream();

        this.updater = updater;
        paint = new PaintThread();
        paint.start();
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
                    updater.update(value);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    interface  Updatable{
        public void update(double value) throws IOException;
    }
    public void sendAction(int action) throws IOException {
        out.write(action);
    }
    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
}
