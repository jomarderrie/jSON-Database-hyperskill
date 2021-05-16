package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    private static final int PORT = 34522;

    public static void main(String[] args) {
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT)) {

                try (
                        Socket socket = server.accept(); // accepting a new client
                        DataInputStream input = new DataInputStream(socket.getInputStream());
                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
                ) {
                    String msg = input.readUTF(); // reading a message
                    System.out.println("Received: " + msg);

                    String stringMessage = "Sent: A record # 12 was sent!";
                    System.out.println(stringMessage);
                    output.writeUTF(stringMessage); // resend it to the client
                }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
