package client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class Client {
    Write output;
    InputStream in;
    OutputStream out;
    Client(Write writeFX){
        this.output = writeFX;
    }
    public void start() throws IOException, InterruptedException {
        new Thread(() -> {
            try {
        Socket s = new Socket("127.0.0.1", 8000);
                in = s.getInputStream();
                out = s.getOutputStream();

                output.writeFX(stringRead(in)); // Enter name
                while (in.read() != 1) {
                    output.writeFX(stringRead(in)); // Enter count of matches
                    output.writeFX(stringRead(in));
                }
                output.writeFX(stringRead(in)); // Who is winner
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
    }).start();
    }

    public static void stringWrite(OutputStream out, String str) throws IOException {
        int count = str.length();
        out.write(count);
        byte[] b = str.getBytes();
        out.write(b);
    }
    public void send(String str, int option) throws IOException {
        new Thread(() -> {
            try {
            if(option == 1)  // int sending
            {
                System.out.println(str);
                System.out.println(Integer.valueOf(str).intValue());
                out.write(Integer.valueOf(str).intValue());
            }
            else // string sending
                stringWrite(out, str);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public static String stringRead(InputStream in) throws IOException {
        int count = in.read();
        byte[] b = new byte[count];
        in.read(b);
        return new String(b);
    }
    public interface Write {
        public void writeFX(String str);
    }

}

