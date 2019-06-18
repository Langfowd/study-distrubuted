package netty.tomcat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import netty.tomcat.http.MyRequest;
import netty.tomcat.http.MyResponse;
import netty.tomcat.servlet.MyServlet;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyTomcat {

    private int port = 8080;

    private Map<String, MyServlet> servletMap = new HashMap<String, MyServlet>();

    public static void main(String[] args) {
        new MyTomcat().start();
    }

    private void init() {
        try {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("web.properties");
        Properties properties = new Properties();
        properties.load(resourceAsStream);
            for (Map.Entry<Object, Object> objectEntry : properties.entrySet()) {
                String key = (String) objectEntry.getKey();
                if (key.endsWith(".url")) {
                    String value = (String) objectEntry.getValue();
                    String className = properties.getProperty(key.replace(".url", ".class"));
                    Object o = Class.forName(className).newInstance();
                    if (!(o instanceof MyServlet)) {
                        throw new RuntimeException("not support not MyServlet type");
                    }
                    servletMap.put(value, (MyServlet) o);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void start() {
        init();
        // Netty封装了NIO，Reactor模型，Boss，worker
        // Boss线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // Worker线程
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            // Netty服务
            //ServetBootstrap   ServerSocketChannel
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    // 主线程处理类
                    .channel(NioServerSocketChannel.class)
                    // 子线程处理类 , Handler
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new HttpResponseEncoder());
                            socketChannel.pipeline().addLast(new HttpRequestDecoder());
                            socketChannel.pipeline().addLast(new CustomChannelHander());
                        }
                    })
                    // 针对主线程的配置 分配线程最大数量 128
                    .option(ChannelOption.SO_BACKLOG,128)
                    // 针对子线程的配置 保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE,true);
            // 启动服务器
            ChannelFuture sync = serverBootstrap.bind(port).sync();
            System.out.println("Tomcat 已启动，监听的端口是：" + port);
            // 关闭连接
            sync.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    private class CustomChannelHander extends ChannelInboundHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            if (msg instanceof HttpRequest) {
                HttpRequest httpRequest = (HttpRequest) msg;
                MyRequest request = new MyRequest(ctx,httpRequest);
                MyResponse response = new MyResponse(ctx,httpRequest);
                String path = request.getUrl();
                if (servletMap.containsKey(path)) {
                    servletMap.get(path).service(request, response);
                } else {
                    response.write("404 Not Found");
                }
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            System.err.println(cause.getMessage());
            super.exceptionCaught(ctx, cause);
        }
    }


}
