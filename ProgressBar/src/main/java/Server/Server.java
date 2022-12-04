package Server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
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
            model.doAction(action);
        }
    }
}
