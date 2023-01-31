package top.catium.csp.game.message;

import java.util.ArrayList;

public class Message {

    enum MessageType {
        TEXT,
        IMG_URL
    }

    public ArrayList<MessageNode> nodes = new ArrayList<>();

    public String toString() {
        String s = "";
        for (MessageNode node : nodes) {
            s += node.toString();
        }
        return s;
    }
}
