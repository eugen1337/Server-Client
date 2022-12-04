import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class Client {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("127.0.0.1",8000);
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();

        if(in.read() == 1)
            System.out.print("Connection is очень very good\n");

        Scanner scanner = new Scanner(System.in);
        int isContinued = 1;

        while (isContinued != 0) {
            double number;
            //first num scanning and sending
            System.out.print(stringRead(in));
            number = scanner.nextDouble();
            out.write(toByteArray(number));

            //second num scanning and sending
            System.out.print(stringRead(in));
            number = scanner.nextDouble();
            out.write(toByteArray(number));

            // action chose
            System.out.print(stringRead(in));
            int action = scanner.nextInt();
            while (action < 1 || action > 4) {
                action = scanner.nextInt();
            }
            out.write(action);

            //result
            System.out.print(stringRead(in));

            //Continue?
            System.out.print(stringRead(in));
            isContinued = scanner.nextInt();
            out.write(isContinued);
        }
        in.close();
        out.close();
        s.close();
    }
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
        byte[] b = new byte[count];
        in.read(b);
        return new String(b);
    }
}
