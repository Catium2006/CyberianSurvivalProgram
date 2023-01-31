package top.catium.csp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.catium.csp.file.TextFile;
import top.catium.csp.game.item.Item;
import top.catium.csp.game.player.Player;
import top.catium.csp.net.HTTPListener;


public class CyberianSurvivalProgram {
    public static JSONObject config;

    public static HTTPListener httpListener;

    public static int httpPort;

    public static boolean debug;

    static {
        System.out.println("[static] CyberianSurvivalProgram class loaded");
    }

    public static void start() {
        Main.localLogger.info("main program started!");
        Main.localLogger.info("+---------------------------+");
        Main.localLogger.info("| Cyberian Survival Program |");
        Main.localLogger.info("|        version 1.0        |");
        Main.localLogger.info("+---------------------------+");

        Main.localLogger.info("reading config.json");

        String configStr = TextFile.read("./config.json");
        config = JSON.parseObject(configStr);

        httpPort = config.getInteger("httpPort");
        httpListener = new HTTPListener(httpPort);
        httpListener.start();

        debug = config.getBooleanValue("debug");

        Item.loadAll();
//        Item.saveAll();
        Player.loadAll();
//        Player.saveAll();

        new Thread(){
            @Override
            public void run(){
                super.run();
                while(true){
                    try {
                        Thread.sleep(30000);
                        Player.saveAll(); // 自动存档
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }.start();

    }


}
