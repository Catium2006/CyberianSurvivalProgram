package top.catium.csp.net.api;

import com.alibaba.fastjson.JSONObject;
import top.catium.csp.net.HTTPResponse;

public class PostLogin {
    public static HTTPResponse apply(JSONObject jsonObject) {
        if (jsonObject.containsKey("type") && jsonObject.containsKey("playerName") && jsonObject.containsKey("password")) {
            if (jsonObject.getString("type").equals("login")) {

            } else if (jsonObject.getString("type").equals("register")) {

            }
        }
        return new HTTPResponse(400, "Bad Request");
    }

}
