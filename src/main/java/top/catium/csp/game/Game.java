package top.catium.csp.game;

import top.catium.csp.Main;
import top.catium.csp.game.message.Message;

public class Game {
    static {
        Main.localLogger.info("Game class loaded.");
    }

    /**
     * 处理消息, 返回服务器答复(内容可能为空)
     * @param msg
     * @return
     */
    public Message exe(Message msg) {
        Message reply = new Message();
        reply.sender = "server";


        return reply;
    }


}
