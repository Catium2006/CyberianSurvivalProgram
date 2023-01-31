package top.catium.csp.game.player;

import com.alibaba.fastjson.JSON;
import top.catium.csp.Main;
import top.catium.csp.file.TextFile;
import top.catium.csp.game.item.Item;
import top.catium.csp.util.MD5Util;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Player {

    static {
        Main.localLogger.info("Player class loaded.");
    }

    protected static Map<String, Player> playerMap = new HashMap<>(); // 保存所有玩家, 对玩家的一切操作需要访问其中元素

    /**
     * 判定玩家是否存在
     *
     * @param playerName
     * @return 是否存在
     */
    public static boolean isPlayer(String playerName) {
        return playerMap.containsKey(playerName);
    }

    /**
     * 从./players目录加载全部玩家数据
     *
     * @return 成功加载的玩家总数
     */
    public static int loadAll() {
        File dir = new File("./players");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int count = 0;
        for (File f : dir.listFiles()) {
            if (f.getName().endsWith(".json")) {
                String s = TextFile.read(f.getPath());
                if (JSON.parseObject(s, Player.class).load()) {
                    count++;
                } else {
                    Main.localLogger.warn(f.getName() + " load failed as player");
                }
            }
        }
        Main.localLogger.info(count + " players loaded.");
        return count;
    }

    /**
     * 保存表中所有玩家信息
     *
     * @return 保存成功的个数
     */
    public static int saveAll() {
        File dir = new File("./players");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int count = 0;
        for (Player p : playerMap.values()) {
            if (p.save()) {
                count++;
            }
        }
        Main.localLogger.info(count + " players saved.");
        return count;
    }

    /**
     * 注册新玩家, 需提供名字
     *
     * @param playerName
     * @return 是否成功
     */
    public static boolean newPlayer(String playerName, String _password) {
        if (isPlayer(playerName)) {
            return false;
        }
        Player p = new Player();
        p.name = playerName;
        p.password = _password;
        p.token = p.generateToken();
        playerMap.put(p.name, p);
        return isPlayer(p.name);
    }

    /**
     * 获取玩家名对应玩家对象, 返回引用
     *
     * @param playerName
     * @return 玩家对象
     */
    public static Player getPlayer(String playerName) {
        return playerMap.get(playerName);
    }

    public String name;
    public String password; // 我是明文储存密码的屑
    public String token; // 用于验证消息发送者身份, 每次登录时变更
    public int health = 20;
    public int hunger = 0;
    public int ex = 0;
    public String location = "新手村";
    public Map<String, Integer> backpack;

    private String generateToken() {
        String ori = "" + System.currentTimeMillis() + name + password;
        return MD5Util.fromString(ori);
    }


    /**
     * 加载此玩家对象到表中, 表中是该对象的引用
     *
     * @return 是否成功
     */
    public boolean load() {
        if (name != null) {
            playerMap.put(name, this);
            return isPlayer(name);
        }
        return false;
    }

    /**
     * 保存玩家信息到文件
     *
     * @return 是否成功
     */
    public boolean save() {
        String s = JSON.toJSONString(this);
        String filename = "./players/" + name + ".json";
        if (TextFile.write(filename, s)) {
            return true;
        }
        Main.localLogger.error("error while saving player" + name + "(" + filename + ")");
        return false;
    }

    public boolean login(String _password) {
        token = null;
        if (password.equals(_password)) {
            token = generateToken();
            return true;
        }
        return false;
    }
}
