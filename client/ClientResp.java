package client;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;



public class ClientResp {

    public static final ClientResp exitCommand = builder().setType("exit").build();


    @Expose
    private final String type;
    @Expose
    private final JsonElement key;
    @Expose
    private final JsonElement value;

    public ClientResp(String type, JsonElement key, JsonElement value) {
        this.type = type;
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Response{" +
                "response='" + type + '\'' +
                ", reason='" + key + '\'' +
                ", value=" + value +
                '}';
    }

    public String getType() {
        return type;
    }

    public JsonElement getKey() {
        return key;
    }

    public JsonElement getValue() {
        return value;
    }

    public static BuilderResp builder() {
        return new BuilderResp();
    }

    public static final class BuilderResp {

        private String type;
        private JsonElement key;
        private JsonElement value;

        public BuilderResp setType(String type) {
            this.type = type;
            return this;
        }

        public BuilderResp setKey(JsonElement key) {
            this.key = key;
            return this;
        }

        public BuilderResp setValue(JsonElement value) {
            this.value = value;
            return this;
        }

        public ClientResp build() {
            return new ClientResp(this.type, this.key, this.value);
        }
    }
}
