import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.nio.ByteBuffer;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1",8000);
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();
        /*int a = in.read();
        if(a == 1)
        System.out.print("Connection is очень very good");*/
        int isContinued = 1;

        while (isContinued != 0) {
            //first num
            System.out.print(stringRead(in));
            double number = scanner.nextDouble();
            out.write(toByteArray(number));
            //second num
            System.out.print(stringRead(in));
            number = scanner.nextDouble();
            out.write(toByteArray(number));
            // action
            System.out.print(stringRead(in));
            int numint = scanner.nextInt();
            while (numint < 1 || numint > 4) {
                numint = scanner.nextInt();
            }
            out.write(numint);
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
        //System.out.println("count = " + count);
        byte[] b = new byte[count];
        in.read(b);
        return new String(b);
    }
}
