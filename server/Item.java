package server;

public class Item {
    private String action;
    private int index;
    private String[] data;

    public Item(String action, int index, String[] data) {
        this.action = action;
        this.index = index;
        this.data = data;
    }

    public Item(String action, int index) {
        this.action = action;
        this.index = index;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String[] getData() {
        return data;
    }

    public void setData(String[] data) {
        this.data = data;
    }
}
