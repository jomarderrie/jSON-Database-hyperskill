package server;

@FunctionalInterface
public interface Operation {
    ServerResp execute();
}

