package hw3.Chart;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * Created by Жека on 1/17/2017.
 */

class Servak extends  Thread {
    @Override
    public void run() {
        try {
            System.out.println("Сервак запущен");
            ServerSocketChannel channel = ServerSocketChannel.open();
            channel.bind(new InetSocketAddress(30000));// порт для ообщений
            SocketChannel socket = channel.accept();
            ByteBuffer buffer = ByteBuffer.allocate(128);
            int bytes = 0;
            while ((bytes = socket.read(buffer)) > 0) {
                buffer.flip();
                String message = new String(buffer.array(), 0, bytes);
                System.out.println(message);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
