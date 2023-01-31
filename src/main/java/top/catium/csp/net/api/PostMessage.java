package top.catium.csp.net.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.catium.csp.game.Game;
import top.catium.csp.game.message.Message;
import top.catium.csp.game.player.Player;
import top.catium.csp.net.HTTPResponse;

public class PostMessage {
    public static HTTPResponse apply(JSONObject jsonObject) {
        if (jsonObject.containsKey("sender") && jsonObject.containsKey("token") && jsonObject.containsKey("message")) {
            String sender = jsonObject.getString("sender");
            String token = jsonObject.getString("token");
            Message message = jsonObject.getObject("message", Message.class);
            if (sender != null && token != null && message != null) {
                if (Player.isPlayer(sender)) {
                    if (Player.getPlayer(sender).token.equals(token)) {
                        // 此时才是有效的身份
                        Response_PostMessage response = new Response_PostMessage();
                        response.reply = Game.exe(message);
                        return new HTTPResponse(200, JSON.toJSONString(response));
                    }
                }
                return new HTTPResponse(403, "Forbidden");
            }
        }
        return new HTTPResponse(400, "Bad Request");
    }
}

class Response_PostMessage {
    boolean read = true;
    Message reply;
}
