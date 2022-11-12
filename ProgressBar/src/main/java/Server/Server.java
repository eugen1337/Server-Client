package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private boolean running = false;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket socket = serverSocket.accept();
        System.out.println("Server launched");
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();

        Model model = new Model(out);

        while (true) {

            int action = in.read();
            System.out.println(action);
            switch (action) {
                case 1:
                    if (!Model.running) {
                        model = new Model(out);
                        model.start();
                        System.out.print("launch and start model");
                    } else
                        model.barThread.setRerun(true);
                    break;
                case 2:
                    model.stop();
                    break;
                case 3:
                    if (model.barThread.getIsPaused()) {
                        model.barThread.setIsPaused(false);
                        synchronized (System.out) {
                            System.out.notify();
                        }
                        //out.write(1);
                    }
                    else {
                        //out.write(2);
                        model.barThread.setIsPaused(true);
                    }
                    break;

        }
    }
    }
}
