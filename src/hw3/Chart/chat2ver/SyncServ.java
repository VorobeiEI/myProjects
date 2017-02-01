package hw3.Chart.chat2ver;

import hw3.Chart.testiki.ServTest;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by Жека on 1/24/2017.
 */
public class SyncServ implements Runnable {

    private static ServerSocketChannel channel;
    private static SocketChannel socket;
    private static String message;
    private static ByteBuffer buffer;

    public static void main(String[] args) throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(41000));
        socket = channel.accept();
        buffer = ByteBuffer.allocate(128);
        Thread ser = new Thread(new SyncServ());
        ser.start();
        while (true) {
            //Отправка сообщения
            Scanner scan = new Scanner(System.in);
            System.out.print("Сообщение для отправки : ");
            message = scan.nextLine();
            buffer.put(message.getBytes());
            buffer.flip();
            try {
                socket.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.clear();
        }
    }//Получение сообщения
    public void run() {
        while (true) {
            int bytes;
            try {
                while ((bytes = socket.read(buffer)) > 0) {
                    buffer.flip();
                    message = new String(buffer.array(), 0, bytes);
                    System.out.println("Входящее сообщение от (клиент) : " + message);
                    buffer.clear();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}





