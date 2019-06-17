package normal.tomcat.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

public class MyResponse {

    private Socket socket;

    private static final String RESPONSE_HTTP = "HTTP/1.1 200 OK\r\nContent-Type: text/html;charset=utf-8\r\n\n";

    public MyResponse(Socket socket) {
        this.socket = socket;
    }

    public void write(String content) {
        if (content == null) {
            throw new RuntimeException("illeage argument for content not support receive null");
        }
        OutputStream outputStream = null;
        try {
            outputStream = socket.getOutputStream();
            outputStream.write((RESPONSE_HTTP+content).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
