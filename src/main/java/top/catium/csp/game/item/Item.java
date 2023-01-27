package top.catium.csp.game.item;

import com.alibaba.fastjson.JSON;
import top.catium.csp.Main;
import top.catium.csp.file.TextFile;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Item {
    protected static Map<String, Item> itemMap = new HashMap<>(); // 所有被加载物品都在此

    /**
     * 判断是否存在物品名对应的物品
     *
     * @param _name
     * @return 判定结果
     */
    public static boolean isItem(String _name) {
        return _name == null ? false : itemMap.containsKey(_name);
    }

    /**
     * 从JSON解析一个物品, 不一定在物品表中存在
     *
     * @param _json
     * @return 物品对象
     */
    public static Item fromJSON(String _json) {
        Item i = JSON.parseObject(_json, Item.class);
        return i;
    }

    /**
     * 获取物品名对应的物品
     *
     * @param _name
     * @return 物品
     */
    public static Item byName(String _name) {
        if (isItem(_name)) {
            // 序列化是为了复制一个新元素 保证itemMap中只读
            String s = JSON.toJSONString(itemMap.get(_name));
            return fromJSON(s);
        }
        return null;
    }

    /**
     * 读取./items目录中所有json文件并解析为Item类, 然后加载到表中
     *
     * @return 加载物品总数
     */
    public static int loadAll() {
        File dir = new File("./items");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int count = 0;
        if (dir.listFiles() != null) {
            for (File f : dir.listFiles()) {
                if (f.getName().endsWith(".json")) {
                    String s = TextFile.read(f.getPath());
                    if (JSON.parseObject(s, Item.class).load()) {
                        count++;
                    } else {
                        Main.localLogger.warn(f.getName() + " load failed as item.");
                    }
                }
            }
        }
        Main.localLogger.info(count + " items loaded.");
        return count;
    }

    /**
     * 保存(覆盖)所有物品配置
     *
     * @return 保存的文件数
     */
    public static int saveAll() {
        File dir = new File("./items");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        int count = 0;
        for (Item i : itemMap.values()) {
            if (i.save()) {
                count++;
            }
        }
        Main.localLogger.info(count + " items saved.");
        return count;
    }

    public String name; // 物品名

    public Map<String, Integer> formula; // 合成表

    /**
     * 加载此对象到表中, 表中是复制的对象而不是引用
     *
     * @return 是否注册成功
     */
    public boolean load() {
        if (name != null) {
            String s = JSON.toJSONString(this);
//            Main.localLogger.info(s);
            Item i = JSON.parseObject(s, Item.class);
            Item.itemMap.put(i.name, i);
            return isItem(name);
        }
        return false;
    }

    public boolean save() {
        String s = JSON.toJSONString(this);
        String filename = "./items/" + name + ".json";
        if (TextFile.write(filename, s)) {
            return true;
        } else {
            Main.localLogger.error("error while saving item" + name + "(" + filename + ")");
            return false;
        }
    }


}
