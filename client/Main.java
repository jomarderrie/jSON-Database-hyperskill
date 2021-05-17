package client;

import com.beust.jcommander.JCommander;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34522;

    public static void main(String[] args) throws IOException {
        System.out.println("Client started!");
        Task task = new Task();
        JCommander.newBuilder()
                .addObject(task)
                .build()
                .parse(args);

        String command = args[1];
        String index;
        String[] message;
        DataInputStream input;
        DataOutputStream output;
        Socket socket;
        String receivedMsg;
        switch (command) {
            case "exit":
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(command);
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                socket.close();
                input.close();
                output.flush();
                output.close();
                break;
            case "set":
                index = args[3];
                message = new String[args.length - 5];
                System.arraycopy(args, 5, message, 0, args.length - 5);
                System.out.println("Sent: " + index + " " + Arrays.toString(Arrays.stream(message).toArray()).replace("[", "")  //remove the right bracket
                        .replace("]", "")
                        .replace(",", ""));
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(command + " " + index + " " + Arrays.toString(message));
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                socket.close();
                input.close();
                output.flush();
                output.close();
                break;
            case "delete":
            case "get":
                index = args[3];
                System.out.println("Sent: " + command + " " + index);
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(command + " " + index);
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                socket.close();
                input.close();
                output.flush();
                output.close();
                break;
        }
    }



}
