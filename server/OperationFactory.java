package server;

import server.database.DatabaseConnection;

public class OperationFactory {
    private final Task task;
    private final Main server;
    private final DatabaseConnection connection;

    public OperationFactory(Task task, Main server, DatabaseConnection connection) {
        this.task = task;
        this.server = server;
        this.connection = connection;
    }

    public Operation createOperation() {
        switch (task.getType()) {
            case "get":
                return () -> connection.get(task);
            case "set":
                return () -> connection.set(task);
            case "delete":
                return () -> connection.delete(task);
            case "exit":
                return server::close;
            default:
                return () -> ServerResp.EMPTY;
        }
    }


}
