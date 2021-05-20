package server;

import com.google.gson.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int PORT = 34523;

    private static final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        System.out.println("Server started!");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ServerSocket serverSocket = new ServerSocket(PORT);
        while (!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();
            executor.submit(() -> {
                try (DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                    String command = in.readUTF();
//                    System.out.println(Thread.currentThread().getName() + " : received: " + command);
                    Task task = new Gson().fromJson(command, Task.class);
                    System.out.println(command);
//                    OperationFactory factory = new OperationFactory(task, this, connection);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
