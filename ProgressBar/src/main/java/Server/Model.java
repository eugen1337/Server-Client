package Server;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class Model {

    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }
    static public boolean running;
    public progressBarThread barThread;

    Model(OutputStream out) {
        barThread = new progressBarThread();
        barThread.setUpdater(new Updatable() {
            public void update(double value) throws IOException {
                out.write(toByteArray(value));
            }
        });

    }
    public void start()
    {
        barThread.start();
        running = true;
    }

    public void stop()
    {
        barThread.setRerun(true);
        barThread.interrupt();
        running = false;
    }
}
interface  Updatable{
    public void update(double value) throws IOException;
}

class progressBarThread extends Thread {
    Updatable updater;
    public void setUpdater(Updatable updater) {this.updater = updater;}
    private boolean isPaused = false;
    public boolean getIsPaused() { return isPaused; }
    public void setIsPaused(boolean isPaused) {  this.isPaused = isPaused; }
    private boolean rerun = false;
    public boolean getRerun() { return rerun; }
    public void setRerun(boolean rerun) { this.rerun = rerun;}
    private double percents = 0;

    @Override
    public void run() {
        try {
            for (int i = 0; i < 1000; i++) {
                if(rerun) {
                    i = 0;
                    rerun = false;
                }
                percents = i * 0.001;

                updater.update(percents);
                Thread.sleep(20);
                if(isPaused) {
                    try {
                        synchronized (System.out) {
                            System.out.wait();
                        }
                    } catch (InterruptedException e) {
                        this.interrupt();
                    }
                }
            }

        }
        catch (InterruptedException | IOException e) {
            this.interrupt();
        }
    }
}
