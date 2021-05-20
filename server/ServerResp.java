package server;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;

public class ServerResp {

    public static final ServerResp EMPTY = builder2()
            .setResponse("ERROR")
            .setReason("No such operation")
            .build();
    public static final ServerResp OK = builder2()
            .setResponse("OK")
            .build();
    public static final ServerResp NO_KEY = builder2()
            .setResponse("ERROR")
            .setReason("No such key")
            .build();


    @Expose
    private final String response;
    @Expose
    private final String reason;
    @Expose
    private final JsonElement value;

    public ServerResp(String response, String reason, JsonElement value) {
        this.response = response;
        this.reason = reason;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                ", reason='" + reason + '\'' +
                ", value=" + value +
                '}';
    }

    public String getResponse() {
        return response;
    }

    public String getReason() {
        return reason;
    }

    public JsonElement getValue() {
        return value;
    }

    public static Builder builder2() {
        return new Builder();
    }

    public static final class Builder {

        private String response;
        private String reason;
        private JsonElement value;

        public Builder setResponse(String response) {
            this.response = response;
            return this;
        }

        public Builder setReason(String reason) {
            this.reason = reason;
            return this;
        }

        public Builder setValue(JsonElement value) {
            this.value = value;
            return this;
        }

        public ServerResp build() {
            return new ServerResp(this.response, this.reason, this.value);
        }
    }
}
