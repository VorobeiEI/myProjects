package hw3.Chart.chatikisgrofoi;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by Жека on 1/25/2017.
 */
public class ServTest extends Thread {

    private   ServerSocketChannel channel;
    private   SocketChannel socket;
    private   String messagesend;
    private   String messagereceive;
    private   ByteBuffer buffer;
    @FXML
    public TextArea txtreceive;
    @FXML
    public TextArea typehere;
    @FXML
    public Button btnstart;

    @FXML
    public void initialize(){
        btnstart.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                try {
                    connect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        typehere.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> keyPressed(keyEvent));

    }

    private void keyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            send();
            typehere.setText(null);
        }
    }

    public  void connect() throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(39000));
        socket = channel.accept();
        if (socket.isConnected()) {
            System.out.println("Клиент подключен!!!");
        }
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                buffer = ByteBuffer.allocate(128);
                while (true) {
                    input(buffer);
                    txtreceive.setText(messagereceive);
                }
            }
        });
        thread2.start();
    }

        public void send(){
             //Отправка сообщения
            messagesend = typehere.getText();
            buffer.put(messagesend.getBytes());
            buffer.flip();
            try {
                socket.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer.clear();
        }

    public String input(ByteBuffer buffer) {
        //Получение сообщения

            int bytes;
            try {
                while ((bytes = socket.read(buffer)) > 0) {
                    buffer.flip();
                    messagereceive = new String(buffer.array(), 0, bytes);
                    System.out.println("Входящее сообщение от (клиент) : \n" + messagereceive);
                    buffer.clear();
                    break;
                }
            } catch (IOException e) {
                System.out.println("Клиент отключился!!!");
            } return messagereceive;

    }
}


