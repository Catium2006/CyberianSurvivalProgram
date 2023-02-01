package top.catium.csp.game.message;

import java.util.ArrayList;

public class Message {

    enum MessageType {
        TEXT,
        IMG_URL
    }

    public ArrayList<MessageNode> nodes = new ArrayList<>();

    public MessageNode indexOf(int index) {
        return nodes.get(index);
    }

    public void append(MessageNode messageNode) {
        nodes.add(messageNode);
    }

    public void append(Message message) {
        for (MessageNode nd : message.nodes) {
            nodes.add(nd);
        }
    }

    public String toString() {
        String s = "";
        for (MessageNode node : nodes) {
            s += node.toString();
        }
        return s;
    }
}
