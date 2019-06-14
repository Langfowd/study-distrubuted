package study.nio.server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class NioServer {

    private int port;
    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
    public NioServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
       new NioServer(8080).listen();
    }

    public void listen() {
        ServerSocketChannel serverSocketChannel = null;
        Selector selector = null;
        try {
            serverSocketChannel= ServerSocketChannel.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.configureBlocking(false);
            selector = Selector.open();
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            for (; ; ) {
                if (selector.select() == 0) {
                    System.out.println("=====");
                    continue;
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    if (selectionKey.isAcceptable()) {
                        handerAccept(selectionKey);
                    }else if (selectionKey.isReadable()) {
                        handerRead(selectionKey);
                    }else if (selectionKey.isWritable()) {
                        handerWrite(selectionKey);
                    }
                    iterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (selector != null) {
                    selector.close();
                }
                if (serverSocketChannel != null) {
                    serverSocketChannel.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void handerWrite(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        String content = (String)selectionKey.attachment();
        channel.write(ByteBuffer.wrap(content.getBytes()));
        channel.close();
    }

    private void handerRead(SelectionKey selectionKey) throws IOException {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        int len = channel.read(byteBuffer);
        if (len > 0) {
            byteBuffer.flip();
            System.out.println("接收到了:"+new String(byteBuffer.array(),0,len));
        }
         selectionKey = channel.register(selectionKey.selector(), SelectionKey.OP_WRITE);
         // 在key上携带一个附件，一会写出去
         selectionKey.attach("nice to meet you too");
    }

    private void handerAccept(SelectionKey selectionKey) throws IOException {
        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
        SocketChannel accept = channel.accept();
        accept.configureBlocking(false);
        accept.register(selectionKey.selector(),SelectionKey.OP_READ);
    }
}
