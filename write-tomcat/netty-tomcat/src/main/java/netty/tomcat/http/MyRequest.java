package netty.tomcat.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class MyRequest {
    ChannelHandlerContext context;

    HttpRequest httpRequest;

    public MyRequest(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.context=ctx;
        this.httpRequest=httpRequest;
    }

    public String getUrl() {
        return httpRequest.uri();
    }

    public String getMethod() {
        return httpRequest.method().name();
    }
}
