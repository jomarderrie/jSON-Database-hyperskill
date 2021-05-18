package server;

import com.google.gson.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Main {
    private static final int PORT = 34523;


    public static void main(String[] args) {
        Gson gson = new GsonBuilder().create();
        HashMap<String, String> jsonDB = new HashMap<>();
        System.out.println("Server started!");
        try (ServerSocket server = new ServerSocket(PORT)) {
            boolean onServerStart = true;
            while (onServerStart) {
                Socket socket = null;
                while (socket == null) {
                    socket = server.accept();
                    DataInputStream input = new DataInputStream(socket.getInputStream());
                    DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                    String msg = input.readUTF();
                    JsonObject jsonObject = JsonParser.parseString(msg).getAsJsonObject();
                    JsonElement hashMapValue;
                    String s = "";
                    JsonElement command = jsonObject.get("type");
                    switch (command.getAsString()) {
                        case "set":
                            jsonDB.put(jsonObject.get("key").getAsString(), jsonObject.get("value").getAsString());
                            output.writeUTF(gson.toJson(ServerResp.OK));
                            output.flush();
                            output.close();
                            socket = null;
                            break;
                        case "delete":
                            hashMapValue = jsonObject.get("key");
                            String value = jsonDB.get(hashMapValue.getAsString());
                            System.out.println(value + " hey" );

                            if (value == null || value.equals("")) {
                                output.writeUTF(gson.toJson(ServerResp.NO_KEY));
                                output.flush();
                                output.close();
                                socket = null;
                                break;
                            } else {
                                jsonDB.remove(hashMapValue.getAsString());
                                output.writeUTF(gson.toJson(ServerResp.OK));
                                output.flush();
                                output.close();
                                socket = null;
                                break;
                            }
                        case "get":
                            hashMapValue = jsonObject.get("key");
                            s = jsonDB.get(hashMapValue.getAsString());

                            if (s == null) {
                                ServerResp response = ServerResp.NO_KEY;
                                output.writeUTF(gson.toJson(response));
                                output.flush();
                                output.close();
                                socket = null;
                                break;
                            } else {
                                ServerResp.Builder builder = new ServerResp.Builder().setResponse("OK").setValue(gson.toJsonTree(s));
                                output.writeUTF(gson.toJson(builder));
                                output.flush();
                                output.close();
                                socket = null;
                                break;
                            }
                        case "exit":
                            output.writeUTF(gson.toJson(ServerResp.OK));
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


    public static void errorMessage() {

    }


}
