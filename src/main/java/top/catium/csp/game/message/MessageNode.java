package top.catium.csp.game.message;

import top.catium.csp.Main;

import static top.catium.csp.game.message.Message.MessageType.IMG_URL;
import static top.catium.csp.game.message.Message.MessageType.TEXT;

public class MessageNode {

    public MessageNode(String text) {
        type = TEXT;
        content = text;
    }

    public MessageNode(Message.MessageType t, String c) {
        type = t;
        content = c;
    }

    public Message.MessageType type;
    public String content;

    public String toString() {
        String s = "";
        if (type == TEXT) {
            s += content;
        }
        if (type == IMG_URL) {
            s += "[img:" + content + "]";
        }
        return s;
    }
}
