package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
    private static final int PORT = 34522;


    public static void main(String[] args) {
        String[] jsonDb = new String[1001];
        Arrays.setAll(jsonDb, i -> "");

        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT)) {
            boolean onServerStart = true;
            while (onServerStart) {
                Socket socket = null;
                while (socket == null) {
                    socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    String[] msg = input.readUTF().split(" ");
                    String command = msg[0];
                    int index = Integer.parseInt(msg[1]);
                    String value;
                    String message = "";
                    switch (command) {
                        case "set":
                            for (int i = 2; i < msg.length; i++) {
                                message += msg[i];
                                message += " ";
                            }
                            message.replace("[", "")
                                    .replace("]", "")
                                    .replace(",","");
                            jsonDb[index] = message;
                            output.writeUTF("OK");
                            break;
                        case "delete":
                            jsonDb[index] = "";
                            output.flush();
                            output.close();
                            socket =null;
                            break;
                        case "get":
                            value = jsonDb[index];
                            if (!value.equals("")){
                                output.writeUTF(value);
                                output.flush();
                                output.close();
                                socket =null;
                                break;
                            }else{
                                output.writeUTF("ERROR");
                                output.flush();
                                output.close();
                                socket =null;
                                break;
                            }
                        case "exit":
                            output.writeUTF("OK");
                            output.flush();
                            output.close();
                            socket =null;
                            onServerStart = false;
                            server.close();
                    }
                    System.out.println(Arrays.toString(msg));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        try (ServerSocket server = new ServerSocket(PORT)) {
//                try (
//                        Socket socket = server.accept(); // accepting a new client
//                        DataInputStream input = new DataInputStream(socket.getInputStream());
//                        DataOutputStream output = new DataOutputStream(socket.getOutputStream())
//                ) {
//                    String msg = input.readUTF(); // reading a message
//                    System.out.println("Received: " + msg);
//
//                    String stringMessage = "Sent: A record # 12 was sent!";
//                    System.out.println(stringMessage);
//                    output.writeUTF(stringMessage); // resend it to the client
//                }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
