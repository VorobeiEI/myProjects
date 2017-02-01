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
import java.nio.channels.SocketChannel;


/**
 * Created by Жека on 1/25/2017.
 */
public class ClientTest extends Thread {

    private   SocketChannel channel;
    private   ByteBuffer buffer ;
    private   String messagesent;
    private   String messagesreceive;
    @FXML
    public Label lblreceive ;
    @FXML
    public TextArea msgout;
    @FXML
    public Button btnconn;

    @FXML
    public void initialize() {
        btnconn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>(){
                    @Override
                    public void handle(MouseEvent event) {
                        startClient();
                    }
                });

        msgout.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> keyPressed(keyEvent));
    }

    public void keyPressed(KeyEvent keyEvent) {
           if (keyEvent.getCode().equals(KeyCode.ENTER)) {
               send();
               msgout.setText(null);
        }
    }

    public void startClient() {
        try {
            channel = SocketChannel.open(new InetSocketAddress("192.168.0.102", 39000));
            if (channel.isConnected()) {
                System.out.println("Покдлючение к серверу прошло успешно!!!");
            }
        } catch (IOException e) {
            System.out.println("Сервак недоступен!!!");
        }
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
               buffer = ByteBuffer.allocate(128);
                while (true){
                    input(buffer);
                }
            }
        });
        thread1.start();
    }

    public void input (ByteBuffer buffer) {
        try {
             int bytes;
               while ((bytes = channel.read(buffer)) > 0) {
                    buffer.flip();
                    messagesreceive = new String(buffer.array(), 0, bytes);
                    //lblreceive.setText(messagesreceive);
                    System.out.println("Входящее сообщение от (сервер) : \n" + messagesreceive);
                    buffer.clear();
                    break;}

        } catch (IOException e) {
          System.out.println("Сервак отключился!!!");

        }
        //return messagesreceive;
    }

    public void send() {
                messagesent = msgout.getText();
                buffer.put(messagesent.getBytes());
                buffer.flip();
                try {
                    channel.write(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                buffer.clear();
                }
    }