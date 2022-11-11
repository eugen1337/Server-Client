import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;

class Server {
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
    public static void stringWrite(OutputStream out, String str) throws IOException {
        int count = str.length();
        out.write(count);
        byte[] b = str.getBytes();
        out.write(b);
    }
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8000);
        Socket socket = serverSocket.accept();
        System.out.println("Server launched");
        OutputStream out = socket.getOutputStream();
        InputStream in = socket.getInputStream();



        double first;
        double second;
        double result;
        byte[] b = new byte[8];
        int isContinued = 1;
        while (isContinued != 0) {
            boolean devByZero = false;
            stringWrite(out, "Type first number \n");
            in.read(b);
            first = toDouble(b);
            stringWrite(out, "Type second number \n");
            in.read(b);
            second = toDouble(b);

            stringWrite(out, "Type number of action you want: 1: +, 2: -, 3: *, 4: / \n");
            switch (in.read()) {
                case 1:
                    result = first + second;
                    break;
                case 2:
                    result = first - second;
                    break;
                case 3:
                    result = first * second;
                    break;
                case 4:
                    result = first / second;
                    if(second == 0)
                        devByZero = true;
                    break;
                default:
                    result = 0;
            }
            System.out.println(result);
            if (devByZero)
                stringWrite(out, "Devision by zero\n");
            else
                stringWrite(out, "Result = " + result + "\n");
            stringWrite(out, "Continue? \n 0 - No\n");
            isContinued = in.read();
        }
        /*
        while(in.read() != -1){
            int i = 0;
            byte c;
            c = (byte)in.read();
            b[i] = c;
            i++;
        }*/
        /*byte b = in.read();
        while (b!=-1) {
            System.out.println("Read: " + b);
            b = in.read();
        }*/

        /*OutputStreamWriter writer = new OutputStreamWriter(out);
        writer.write("HTTP/1.0 200 OK\n" +
                "Content-type: text/html\n" +
                "\n" +
                "<h1>POshel by ty<h1>\n");
        writer.flush();*/





        in.close();
        out.close();
        serverSocket.close();
        socket.close();
    }
}
