package normal.tomcat.servlet;

import normal.tomcat.http.MyRequest;
import normal.tomcat.http.MyResponse;

public interface MyServlet {
    void service(MyRequest request, MyResponse response);
    void doGet(MyRequest request, MyResponse response);
    void doPost(MyRequest request, MyResponse response);
}
