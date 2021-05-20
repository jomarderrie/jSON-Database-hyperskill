package client;

import com.beust.jcommander.JCommander;
import com.google.gson.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 34523;

    public static void main(String[] args) throws IOException {
        Task task = new Task();
        JCommander.newBuilder()
                .addObject(task)
                .build()
                .parse(args);
        Gson gson = new GsonBuilder().create();

        Object object = task;
        if (task.file != null) {
            try (BufferedReader reader = Files.newBufferedReader(Paths.get("client/data/" + task.file))) {
                object = gson.fromJson(reader, Object.class);
            }
        }

        Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        System.out.println("Client started!");
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out  = new DataOutputStream(socket.getOutputStream());

        String request = gson.toJson(object);
        System.out.println("Sent: " + request);
        out.writeUTF(request);
        String response = in.readUTF();
        System.out.println("Received: " + response);
    }
}
