import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static ClientsOnServer clientAccept(ServerSocket serverSocket) throws IOException {
        Socket socket = serverSocket.accept();
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();
        stringWrite(out, "Type your name");
        String name = stringRead(in);
        ClientsOnServer client = new ClientsOnServer(out, in, name);
        ClientsOnServer.count ++;
        System.out.println("Client #" + ClientsOnServer.count + " is accepted - ");
        System.out.println(name);
        return client;
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        System.out.println("Server launched");
        ClientsOnServer[] clients = new ClientsOnServer[2];
        clients[0] = clientAccept(serverSocket);
        clients[1] = clientAccept(serverSocket);
        int mathes = 37;
        int a = (int) ( 1 + Math.random() * 2 );
        while (mathes > 0)
        {
            a = (a + 1) % 2;
            stringWrite(clients[a].out, "Type count of matches you want to pick up, remaining" + mathes);
            mathes -= clients[a].in.read();
        }
        clients[0].out.write(1);
        clients[1].out.write(1);
        stringWrite(clients[0].out, "Gamer " + clients[a].name + " win");
        stringWrite(clients[1].out, "Gamer " + clients[a].name + " win");

    }

    public static void stringWrite(OutputStream out, String str) throws IOException {
        int count = str.length();
        out.write(count);
        byte[] b = str.getBytes();
        out.write(b);
    }
    public static String stringRead(InputStream in) throws IOException {
        int count = in.read();
        //System.out.println("count = " + count);
        byte[] b = new byte[count];
        in.read(b);
        return new String(b);
    }
}

