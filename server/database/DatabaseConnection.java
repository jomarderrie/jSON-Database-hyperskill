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
    public synchronized ServerResp get(Task task) {
        List<String> keys = task.getKeys();
        List<Record> list = operator.read();
        Record record = list.stream()
                .filter(r -> r.getKey().equals(keys.get(0)))
                .findFirst()
                .orElse(null);

        if (record == null) {
            return ServerResp.NO_KEY;
        }

        JsonElement elem = record.getValue();
        for (int i = 1; i < keys.size(); i++) {
            if (elem.isJsonObject()) {
                elem = elem.getAsJsonObject().get(keys.get(i));
            } else if (elem.isJsonPrimitive()) {
                elem = elem.getAsJsonPrimitive();
            }
        }

        return ServerResp.builder2()
                .setResponse("OK")
                .setValue(elem)
                .build();
    }

    @Override
    public synchronized ServerResp set(Task task) {
        List<String> keys = task.getKeys();
        List<Record> list = operator.read();
        Record record = list.stream()
                .filter(r -> r.getKey().equals(keys.get(0)))
                .findFirst()
                .orElse(null);

        if (record == null) {
            list.add(new Record(keys.get(0), task.getValue()));
        } else if (keys.size() >= 2) {
            JsonElement elem = record.getValue();
            for (int i = 1; i < keys.size() - 1; i++) {
                if (elem.isJsonObject()) {
                    elem = elem.getAsJsonObject().get(keys.get(i));
                }
            }
            if (elem.isJsonObject()) {
                if (task.getValue().isJsonPrimitive()) {
                    elem.getAsJsonObject().addProperty(keys.get(keys.size() - 1), task.getValue().getAsString());
                } else if (task.getValue().isJsonObject()) {
                    System.out.println("?????????????????????????");
                }
            }
        } else {
            int index = list.indexOf(record);
            list.set(index, new Record(keys.get(0), task.getValue()));
        }
        operator.save(list);
        return ServerResp.OK;
    }

    @Override
    public synchronized ServerResp delete(Task task) {
        List<String> keys = task.getKeys();
        List<Record> list = operator.read();
        Record record = list.stream()
                .filter(r -> r.getKey().equals(keys.get(0)))
                .findFirst()
                .orElse(null);

        if (record == null) {
            return ServerResp.NO_KEY;
        }

        if (keys.size() >= 2) {
            JsonElement elem = record.getValue();
            for (int i = 1; i < keys.size() - 1; i++) {
                if (elem.isJsonObject()) {
                    elem = elem.getAsJsonObject().get(keys.get(i));
                }
            }
            elem.getAsJsonObject().remove(keys.get(keys.size() - 1));
        } else {
            list.remove(record);
        }

        operator.save(list);
        return ServerResp.OK;
    }
}
