package top.catium.csp.game.message;

import top.catium.csp.Main;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static top.catium.csp.game.message.Message.MessageType.IMG_URL;
import static top.catium.csp.game.message.Message.MessageType.TEXT;

public class Message {
    static {
        Main.localLogger.info("Message class loaded.");
    }

    enum MessageType {
        TEXT,
        IMG_URL
    }

    public String sender = "";
    public ArrayList<MessageNode> message = new ArrayList<>();

    public String toString() {
        String s = "";
        for (MessageNode node : message) {
            s += node.toString();
        }
        return s;
    }
}
