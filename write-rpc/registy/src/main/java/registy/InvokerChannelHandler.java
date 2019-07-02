package registy;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.HandlerProtocol;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InvokerChannelHandler extends ChannelInboundHandlerAdapter {

    Map<String,Object> beanMaps = new ConcurrentHashMap<String, Object>();

    List<String> classNames = new ArrayList<String>();

    public InvokerChannelHandler() {
        //  扫描包
        try {
            doScannerPackage("provider");
            doRegisty();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doRegisty() {
        for (String className : classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                beanMaps.put(aClass.getInterfaces()[0].getName(),aClass.newInstance());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doScannerPackage(String pakage) throws ClassNotFoundException {
        URL url = this.getClass().getClassLoader().getResource(pakage.replaceAll("\\.","/"));
        File file = new File(url.getFile());
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (file1.isDirectory()) {
                doScannerPackage(pakage + "." + file1.getName());
            } else {
                String className = pakage+"."+file1.getName().replace(".class","");
                classNames.add(className);
            }
        }
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        HandlerProtocol protocol = (HandlerProtocol) msg;
        String className = protocol.getClassName();
        Object result = null;
        if (beanMaps.containsKey(className)) {
            Object o = beanMaps.get(className);
            Class<?> aClass = Class.forName(className);
            Method method = aClass.getMethod(protocol.getMethodName(), protocol.getParamTypes());
            result = method.invoke(o, protocol.getParamValues());
        }
        ctx.writeAndFlush(result);
        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
