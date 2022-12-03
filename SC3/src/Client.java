import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Socket s = new Socket("127.0.0.1", 8000);
        InputStream in = s.getInputStream();
        OutputStream out = s.getOutputStream();

        System.out.println( stringRead(in));
        stringWrite(out, scanner.nextLine());
        while (in.read() != 1)
        {
            System.out.println(stringRead(in));
            System.out.println(stringRead(in));
            int count = scanner.nextInt();
            while (count < 1 || count > 5) {
                System.out.println( "invalid count, type again" );
                count = scanner.nextInt();
            }
            out.write(count);
        }
        System.out.println( stringRead(in) );
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
