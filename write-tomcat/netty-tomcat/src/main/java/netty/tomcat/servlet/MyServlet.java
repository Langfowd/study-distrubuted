package netty.tomcat.servlet;

import netty.tomcat.http.MyRequest;
import netty.tomcat.http.MyResponse;

public interface MyServlet {
    void service(MyRequest request, MyResponse response);
    void doGet(MyRequest request, MyResponse response);
    void doPost(MyRequest request, MyResponse response);
}
