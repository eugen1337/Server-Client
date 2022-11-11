import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class Client {
    public static void stringWrite(OutputStream out, String str) throws IOException {
    int count = str.length();
    out.write(count);
    byte[] b = str.getBytes();
    out.write(b);
}
    public static byte[] toByteArray(double value) {
        byte[] bytes = new byte[8];
        ByteBuffer.wrap(bytes).putDouble(value);
        return bytes;
    }

    public static double toDouble(byte[] bytes) {
        return ByteBuffer.wrap(bytes).getDouble();
    }
    public static String stringRead(InputStream in) throws IOException {
        int count = in.read();
        System.out.println("count = " + count);
        byte[] b = new byte[count];
        in.read(b);
        return new String(b);
    }

    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1",8000);
        System.out.println("Local port: " +  s.getLocalPort());
        System.out.println("Remote port: " + s.getPort());
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        int a = in.read();
        if(a == 1)
            System.out.print("Connection is очень very good");


        Scanner scanner = new Scanner(System.in);
        //System.out.print("Input a number: ");
        //int num = scanner.nextInt();
        stringRead(in);
        int num = scanner.nextInt();
        stringRead(in);
        num = scanner.nextInt();
        stringRead(in);




        in.close();
        out.close();
        s.close();
    }
}
