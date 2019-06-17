package normal.tomcat.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class MyRequest {
    private String method;
    private String path;


    public MyRequest(Socket socket) {
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            String content;
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            if ((content = reader.readLine()) != null) {
                String[] split = content.split("\\s");
                method = split[0];
                path = split[1];
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the value of method.
     *
     * @return the value of method
     */
    public String getMethod() {
        return method;
    }

    /**
     * Sets the method.
     *
     * @param method method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Gets the value of path.
     *
     * @return the value of path
     */
    public String getPath() {
        return path;
    }

    /**
     * Sets the path.
     *
     * @param path path
     */
    public void setPath(String path) {
        this.path = path;
    }
}
