package server;

import com.google.gson.*;
import server.database.DatabaseConnection;

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
    private ServerSocket serverSocket;
    private  final ExecutorService executor = Executors.newCachedThreadPool();

    public static void main(String[] args) throws IOException {
        new Main().run();
    }

    public void run() throws IOException {
        System.out.println("Server started!");
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        serverSocket = new ServerSocket(PORT);
        DatabaseConnection connection = new DatabaseConnection("C:\\Users\\kimjongun\\IdeaProjects\\JSON Database\\JSON Database\\task\\src\\server\\data\\db.json", gson);
        while (!serverSocket.isClosed()){
            Socket socket = serverSocket.accept();
            executor.submit(() -> {
                try (DataInputStream in = new DataInputStream(socket.getInputStream());
                     DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {
                    String command = in.readUTF();
                    Task task = new Gson().fromJson(command, Task.class);
//                    System.out.println(task.getKey().getAsString());
//                    System.out.println(task.getValue().getAsString());
                    OperationFactory factory = new OperationFactory(task, this, connection);
                    ServerResp serverResp = factory.createOperation().execute();

                    String textResponse = new GsonBuilder().create().toJson(serverResp);
                    out.writeUTF(textResponse);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public ServerResp close() {
        this.executor.shutdown();
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ServerResp.OK;
    }
}
