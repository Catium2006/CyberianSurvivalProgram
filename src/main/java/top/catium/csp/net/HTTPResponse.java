package top.catium.csp.net;

public class HTTPResponse {
    public int code;
    public String str;

    public HTTPResponse(int c, String s) {
        code = c;
        str = s;
    }
}
