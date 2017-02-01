package hw3.Chart.chat2ver;

import hw3.Chart.testiki.ClientTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by Жека on 1/24/2017.
 */
public class SyncClient implements Runnable {

    private static SocketChannel channel;
    private static ByteBuffer buffer;
    private static String message;

    public static void main(String[]args) throws IOException {
        channel = SocketChannel.open(new InetSocketAddress("192.168.0.102", 41000));
        buffer = ByteBuffer.allocate(128);
        Thread cln = new Thread(new SyncClient());
        cln.start();

        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.print("Сообщение для отправки : ");
            message = scan.nextLine();
            buffer.put(message.getBytes());
            buffer.flip();
            try {
                channel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.clear();
        }
    }
    @Override
    public void run() {
        while (true) {
            int bytes;
            try {
                while ((bytes = channel.read(buffer)) > 0) {
                    buffer.flip();
                    message = new String(buffer.array(), 0, bytes);
                    System.out.println("Входящее сообщение от (сервер) : " + message);
                    buffer.clear();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
