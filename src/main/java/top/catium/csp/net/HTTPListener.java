package top.catium.csp.net;

import com.alibaba.fastjson.JSON;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import top.catium.csp.CyberianSurvivalProgram;
import top.catium.csp.Main;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

public class HTTPListener {
    static {
        Main.localLogger.info("HTTPListener class loaded.");
    }

    public HttpServer httpServer;

    public HTTPListener(int port) {
        Main.localLogger.info("HTTPListener port is setting to " + port);
        try {
            // 创建HttpServer服务器
            httpServer = HttpServer.create(new InetSocketAddress(port), 10);
            //将 / 请求交给MyHandler处理器处理
            httpServer.createContext("/", new MyHandler());
        } catch (IOException e) {
            Main.localLogger.error(e.getStackTrace().toString());
        }
    }

    public void start() {
        Main.localLogger.info("HTTPListener started.");
        httpServer.start();
    }

    public void stop() {
        Main.localLogger.info("HTTPListener stopped.");
        httpServer.stop(10);
    }

}

class MyHandler implements HttpHandler {
    public void handle(HttpExchange httpExchange) throws IOException {

        if (CyberianSurvivalProgram.debug) {
            Main.localLogger.info("[" + httpExchange.getRequestMethod() + "] " + httpExchange.getRequestURI().toString());
        }

        String content = "your request method is ["+httpExchange.getRequestMethod()+"] , resource is "+httpExchange.getRequestURI().toString();

//        String content = JSON.toJSONString(httpExchange);

        //设置响应头属性及响应信息的长度
        httpExchange.sendResponseHeaders(200, content.length());
        //获得输出流
        OutputStream os = httpExchange.getResponseBody();
        os.write(content.getBytes());
        os.close();
    }
}