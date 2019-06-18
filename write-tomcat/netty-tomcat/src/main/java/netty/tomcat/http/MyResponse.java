package netty.tomcat.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MyResponse {

    ChannelHandlerContext context;

    HttpRequest httpRequest;


    public MyResponse(ChannelHandlerContext ctx, HttpRequest httpRequest) {
        this.context=ctx;
        this.httpRequest=httpRequest;
    }

    public void write(String content) {
        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(content.getBytes()));
        response.headers().set("Content-Type","text/html;charset=utf8");
        context.writeAndFlush(response);
        context.close();
    }
}
