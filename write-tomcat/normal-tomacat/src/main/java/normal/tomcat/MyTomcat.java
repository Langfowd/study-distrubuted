package normal.tomcat;

import normal.tomcat.http.MyRequest;
import normal.tomcat.http.MyResponse;
import normal.tomcat.servlet.MyServlet;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
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
        try {
            ServerSocket socket = new ServerSocket(port);
            while (true) {
                Socket accept = socket.accept();
                MyRequest request = new MyRequest(accept);
                MyResponse response = new MyResponse(accept);
                if (servletMap.containsKey(request.getPath())) {
                    System.out.println("request method:" + request.getMethod() + "  request path :" + request.getPath());
                    servletMap.get(request.getPath()).service(request, response);
                } else {
                    response.write("404 Not Found");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
