package client;

import com.beust.jcommander.JCommander;
import com.google.gson.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34523;

    public static void main(String[] args) throws IOException {
        System.out.println("Client started!");
        Gson gson = new GsonBuilder().create();
        Task task = new Task();

        JCommander.newBuilder()
                .addObject(task)
                .build()
                .parse(args);
        String command = args[1];
        JsonElement index;
        String[] message;
        DataInputStream input;
        DataOutputStream output;
        Socket socket;
        String s;
        String receivedMsg;
        ClientResp clientResponse;
        switch (command) {
            case "exit":
                System.out.println("Sent: " + command);
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(gson.toJson(ClientResp.exitCommand));
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                socket.close();
                input.close();
                output.flush();
                output.close();
                break;
            case "set":
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                index = gson.toJsonTree(args[3]);
                message = new String[args.length - 5];
                System.arraycopy(args, 5, message, 0, args.length - 5);
                String formattedString = Arrays.toString(message)
                        .replace("[", "")
                        .replace("]", "")
                        .trim();
                clientResponse = new ClientResp(command, index, gson.toJsonTree(formattedString));
                s = gson.toJson(clientResponse);
                System.out.println("Sent: " + s);
                output.writeUTF(s);
                receivedMsg = input.readUTF();
                System.out.println("Received: " + receivedMsg);
                socket.close();
                input.close();
                output.flush();
                output.close();
                break;
            case "delete":
            case "get":
                index = gson.toJsonTree(args[3]);
                if (command.equals("get")) {
                    clientResponse = new ClientResp.BuilderResp().setKey(index).setType("get").build();
                }else{
                    clientResponse = new ClientResp.BuilderResp().setKey(index).setType("delete").build();
                }

                s = gson.toJson(clientResponse);
                System.out.println("Sent: " + s);
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());
                output.writeUTF(s);
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
