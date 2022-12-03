package client;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    Read input;
    Write output;
    InputStream in;
    OutputStream out;
    Client(/*Read readFX,*/ Write writeFX){
        //this.input = readFX;
        this.output = writeFX;
    }
    public void start() throws IOException, InterruptedException {
        new Thread(() -> {
            try {
        Scanner scanner = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1", 8000);
                in = s.getInputStream();

                out = s.getOutputStream();

                output.writeFX(stringRead(in));
                while (in.read() != 1) {
                    output.writeFX(stringRead(in));
                    output.writeFX(stringRead(in));
                }
                output.writeFX(stringRead(in));
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
        /*
        for (int i = 0; i < 7; i++)
        {
            // Creating an array of characters from a query string and filling the empty cells with '0'
            chars[i] = (i < len) ? str.charAt(i) : '0';

            try
            {
                out.write(chars[i]); // Transfer every char in the array like an integer number
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }*/
    }
    public void send(String str, int option) throws IOException {
        new Thread(() -> {
            try {
            if(option == 1) {
                System.out.println(str);
                System.out.println(Integer.valueOf(str).intValue());
                out.write(Integer.valueOf(str).intValue());
            }
            else
                stringWrite(out, str);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }
    public static String stringRead(InputStream in) throws IOException {
        int count = in.read();
        //System.out.println("count = " + count);
        byte[] b = new byte[count];
        in.read(b);
        return new String(b);
    }

    public interface Read {
        public String readFX(TextField textField) throws InterruptedException;
    }
    public interface Write {
        public void writeFX(String str);
    }

}

