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
        String[] jsonDb = new String[1002];
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
                    int index;
                    String value;
                    String message = "";
                    switch (command) {
                        case "set":
                            index  = parseInt(msg[1]);
                            System.out.println(index);
                            if (index==-1){
                                output.writeUTF("ERROR");
                                output.flush();
                                output.close();
                                socket =null;
                                break;
                            }
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
                            index  = parseInt(msg[1]);
                            System.out.println(index);
                            if (index==-1){
                                output.writeUTF("ERROR");
                                output.flush();
                                output.close();
                                socket =null;
                                break;
                            }
                            jsonDb[index] = "";
                            output.writeUTF("OK");
                            output.flush();
                            output.close();
                            socket =null;
                            break;
                        case "get":
                            index  = parseInt(msg[1]);
                            if (index==-1){
                                output.writeUTF("ERROR");
                                output.flush();
                                output.close();
                                socket =null;
                                break;
                            }
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
                            server.close();
                            onServerStart = false;
                            break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int parseInt(String intToParse) {
        int a = Integer.parseInt(intToParse);
        if (a > 1000 || a < 0) {
            return -1;
        } else {
            return a;
        }
    }
}
