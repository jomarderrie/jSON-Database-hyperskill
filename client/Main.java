package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34522;

    public static void main(String[] args) {
        System.out.println("Client started!");
        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            Scanner scanner = new Scanner(System.in);
//            String msg = scanner.nextLine();

            System.out.println("Sent: Give me a record # 12");
            output.writeUTF("Give me a record # 12"); // sending message to the server
            String receivedMsg = input.readUTF(); // response message
            System.out.println("Received: A record # 12 was sent!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
