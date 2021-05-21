package server.io;

import java.util.List;

public interface FileOperations<T> {
    List<T> read();
    void save(List<T> list);
}
