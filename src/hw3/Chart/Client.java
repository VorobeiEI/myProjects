package hw3.Chart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * Created by Жека on 1/18/2017.
 */
public class Client extends Thread{
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    @Override
    public void run() {
        System.out.println("Клиент запущен");
        try {
        SocketChannel socket = SocketChannel.open(new InetSocketAddress("192.168.0.102", 30000));
        ByteBuffer buffer = ByteBuffer.allocate(1024);//единоразовая порция даних
        while (true) {
            buffer.put(reader.readLine().getBytes());
            buffer.flip();
            socket.write(buffer);
            buffer.clear();}
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
