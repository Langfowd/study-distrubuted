package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import protocol.HandlerProtocol;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RpcProxy {

    public static <T> T getProxy(Class<T> tClass) {
        return (T) Proxy.newProxyInstance(RpcProxy.class.getClassLoader(),new Class[]{tClass},new ClientHandler());
    }

    private static class ClientHandler implements InvocationHandler {
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            HandlerProtocol protocol = new HandlerProtocol();
            protocol.setClassName(method.getDeclaringClass().getName());
            protocol.setMethodName(method.getName());
            protocol.setParamValues(args);
            protocol.setParamTypes(method.getParameterTypes());

            NioEventLoopGroup workGroup = new NioEventLoopGroup();
            Bootstrap bootstrap = new Bootstrap();
            final ClientInvokerHandler clientInvokerHandler = new ClientInvokerHandler();
            bootstrap.group(workGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            //自定义协议解码器
                            /** 入参有5个，分别解释如下
                             maxFrameLength：框架的最大长度。如果帧的长度大于此值，则将抛出TooLongFrameException。
                             lengthFieldOffset：长度字段的偏移量：即对应的长度字段在整个消息数据中得位置
                             lengthFieldLength：长度字段的长度。如：长度字段是int型表示，那么这个值就是4（long型就是8）
                             lengthAdjustment：要添加到长度字段值的补偿值
                             initialBytesToStrip：从解码帧中去除的第一个字节数
                             */
                            socketChannel.pipeline()
                            .addLast("frameDecoder",new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,0,4,0,4))
                            .addLast("frameEncoder",new LengthFieldPrepender(4))
                            //对象参数类型编码器
                            .addLast("encoder", new ObjectEncoder())
                            //对象参数类型解码器
                            .addLast("decoder", new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                            .addLast("handler",clientInvokerHandler);
                        }
                    }).option(ChannelOption.TCP_NODELAY,true);
            ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
            future.channel().writeAndFlush(protocol).sync();
            future.channel().closeFuture().sync();
            workGroup.shutdownGracefully();
            return clientInvokerHandler.getResponse();
        }
    }


}
