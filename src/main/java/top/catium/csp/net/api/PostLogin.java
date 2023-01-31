package top.catium.csp.net.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.catium.csp.game.player.Player;
import top.catium.csp.net.HTTPResponse;

import java.util.HashMap;
import java.util.Map;

public class PostLogin {
    public static HTTPResponse apply(JSONObject jsonObject) {
        if (jsonObject.containsKey("type") && jsonObject.containsKey("playerName") && jsonObject.containsKey("password")) {
            String playerName = jsonObject.getString("playerName");
            String password = jsonObject.getString("password");
            if (playerName != null && password != null) {
                //此时格式正确

                Response response = new Response();
                if (jsonObject.getString("type").equals("login")) {
                    if (Player.isPlayer(playerName)) {
                        if (Player.getPlayer(playerName).login(password)) {
                            response.success = true;
                            response.token = Player.getPlayer(playerName).token;
                        } else {
                            response.success = false;
                        }
                    }
                } else if (jsonObject.getString("type").equals("register")) {

                }
                return new HTTPResponse(200, JSON.toJSONString(response));
            }
        }
        //此时格式不正确
        return new HTTPResponse(400, "Bad Request");
    }
}

class Response {
    public boolean success;
    public String token;
}