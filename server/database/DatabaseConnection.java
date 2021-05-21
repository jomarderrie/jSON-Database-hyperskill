package server.database;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import server.ServerResp;
import server.Task;
import server.io.FileOperator;
import server.model.Record;

import java.util.Arrays;
import java.util.List;

public class DatabaseConnection implements Connection {

    private final FileOperator operator;

    public DatabaseConnection(String file, Gson gson) {
        this.operator = new FileOperator(file, gson);
    }


    @Override
    public ServerResp get(Task task) {
        System.out.println(task.getKey());
        String keys = task.getKey().getAsString();
        List<Record> list = operator.read();
        Record record = list.stream()
                .filter(r -> r.getKey().equals(keys))
                .findFirst()
                .orElse(null);

        if (record == null) {
            return ServerResp.NO_KEY;
        }

        return ServerResp.builder2()
                .setResponse("OK")
                .setValue(record.getValue())
                .build();
    }

    @Override
    public ServerResp set(Task task) {
        System.out.println(task.getKey());
        String keys = task.getKey().getAsString();
        List<Record> list = operator.read();
        System.out.println(Arrays.toString(list.toArray()));
        Record record = list.stream()
                .filter(r -> r.getKey().equals(keys))
                .findFirst()
                .orElse(null);
        if (record == null) {
            list.add(new Record(keys, task.getValue()));
            operator.save(list);
            return ServerResp.OK;
        } else {
            return ServerResp.EMPTY;
        }
    }

    @Override
    public ServerResp delete(Task task) {
        String keys = task.getKey().getAsString();
        List<Record> list = operator.read();
        Record record = list.stream()
                .filter(r -> r.getKey().equals(keys))
                .findFirst()
                .orElse(null);
        if (record == null) {
            return ServerResp.NO_KEY;
        } else {
            list.remove(record);
        }
        operator.save(list);
        return ServerResp.OK;
    }
}
