package top.catium.csp.net;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.net.httpserver.*;
import top.catium.csp.CyberianSurvivalProgram;
import top.catium.csp.Main;
import top.catium.csp.net.api.PostLogin;


import java.io.*;
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
            HttpContext context = httpServer.createContext("/", new MyHandler());

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
    /**
     * 从输入流读一个字符串, 默认utf8
     *
     * @param is
     * @return
     */
    private static String readStringFromStream(InputStream is) {
        try {
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            return new String(bytes);
        } catch (IOException e) {
            return "";
        }
    }

    /**
     * 从post正文解析符合要求的json
     *
     * @param origin
     * @return
     */
    private static String getPostJson(String origin) {
        //此处 post 只接受一个[Content-Disposition: form-data; name="json"], 其内容必须为一段json
        String json;
        String target = "Content-Disposition: form-data; name=\"json\"\r\n\r\n";
        if (origin.contains(target)) {
            json = origin.split(target)[1];
            if (json != null) {
                json = json.split("----------------------------")[0];
                return json;
            }
        }
        return null;
    }

    public void handle(HttpExchange httpExchange) throws IOException {
        // 是否返回完了
        boolean finished = false;

        InputStream is = httpExchange.getRequestBody();
        OutputStream os = httpExchange.getResponseBody();

        String method = httpExchange.getRequestMethod();


        if (CyberianSurvivalProgram.debug) {
            Main.localLogger.info("[" + httpExchange.getRequestMethod() + "] " + httpExchange.getRequestURI().toString());
        }

        if (httpExchange.getRequestURI().toString().startsWith("/api/")) {

            String target = httpExchange.getRequestURI().toString().split("/api/")[1];

            //设置响应头属性
            httpExchange.getResponseHeaders().set("Content-Type", "text/html; charset=utf8");
            httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");

            HTTPResponse response = null;

            if (method.equals("POST")) {
                String origin = readStringFromStream(is);
                String json = getPostJson(origin);
                JSONObject jsonObject = JSON.parseObject(json);

                if (json == null || jsonObject == null) {
                    //不是符合要求的格式
                    String s = "Bad Request";
                    httpExchange.sendResponseHeaders(400, s.getBytes().length);
                    os.write(s.getBytes());
                }

                if (target.equals("login")) {
                    response = PostLogin.apply(jsonObject);
                } else {
                    //没有对应的接口
                    response = new HTTPResponse(400,"Bad Request");
                }

            } else if (method.equals("GET")) {

            } else {
                response = new HTTPResponse(405,"Method Not Allowed");
            }


             httpExchange.sendResponseHeaders(response.code, response.str.length());
             os.write(response.str.getBytes());


        } else {

        }

        if (!finished) {
            httpExchange.sendResponseHeaders(500, 0);
        }

        is.close();
        os.close();

    }


}
