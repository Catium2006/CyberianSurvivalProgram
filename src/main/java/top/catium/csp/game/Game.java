package top.catium.csp.game;

import top.catium.csp.Main;
import top.catium.csp.game.message.Message;
import top.catium.csp.game.message.MessageNode;

public class Game {


    /**
     * 处理消息, 返回服务器答复(内容可能为空)
     * @param msg
     * @return
     */
    public static Message exe(Message msg) {

        Message reply = new Message();

        reply.append(new MessageNode("收到捏"));


        return reply;
    }


}
