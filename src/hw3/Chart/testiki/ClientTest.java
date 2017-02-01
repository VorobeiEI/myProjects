package hw3.Chart.testiki;

import hw3.Chart.chat2ver.SyncClient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import org.omg.PortableInterceptor.ServerRequestInfo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * Created by Жека on 1/25/2017.
 */
public class ClientTest extends Thread {

    private static SocketChannel channel;
    private static ByteBuffer buffer;
    private static String message ;
    @FXML
    public TextArea msgall;
    @FXML
    public  TextArea msgout;

    @FXML
    public void initialize() {
        msgout.addEventHandler(KeyEvent.KEY_PRESSED, (keyEvent) -> keyPressed(keyEvent));

    }
    private void keyPressed(KeyEvent keyEvent) {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                msgall.setText(msgout.getText());
            }
    }
    public void startClient(){
        try {
            channel = SocketChannel.open(new InetSocketAddress("192.168.1.116", 37000));
        } catch (IOException e) {
            e.printStackTrace();
        }
        buffer = ByteBuffer.allocate(128);
        if (channel.isConnected()) {
            System.out.println("Покдлючение к серверу прошло успешно!!!");
        }
        ClientTest cln = new ClientTest();
        cln.start();
        while (true) {
            Scanner scan = new Scanner(System.in);
            System.out.println("Сообщение для отправки : ");
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
        try {
            while (true) {
                int bytes;
                while ((bytes = channel.read(buffer)) > 0) {
                    buffer.flip();
                    message = new String(buffer.array(), 0, bytes);
                    System.out.println("Входящее сообщение от (сервер) : \n" + message);
                    buffer.clear();
                    break;
                }
            }
        } catch (IOException e) {
            //e.printStackTrace();
            System.out.println("Сервак отключился!!!");
            stop();
        }
    }
}
