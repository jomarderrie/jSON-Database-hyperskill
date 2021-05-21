package server.database;

import server.ServerResp;
import server.Task;

public interface Connection {
    ServerResp get(Task task);
    ServerResp set(Task task);
    ServerResp delete(Task task);
}
