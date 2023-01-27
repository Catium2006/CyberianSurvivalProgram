package top.catium.csp;


import top.catium.csp.logging.LocalLogger;

import java.io.File;

public class Main {
    public static LocalLogger localLogger;

    public static void main(String[] args) {
        // 首先初始化日志
        localLogger = new LocalLogger("./log");

        // 启动主体
        CyberianSurvivalProgram.start();
    }
}
