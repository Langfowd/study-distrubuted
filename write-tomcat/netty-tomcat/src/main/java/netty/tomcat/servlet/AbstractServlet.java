package netty.tomcat.servlet;

import netty.tomcat.http.MyRequest;
import netty.tomcat.http.MyResponse;

public abstract class AbstractServlet implements MyServlet {

    public void service(MyRequest request, MyResponse response) {
        if ("GET".equals(request.getMethod())) {
            doGet(request,response);
        } else {
            doPost(request,response);
        }
    }

    public abstract void doGet(MyRequest request, MyResponse response);

    public abstract void doPost(MyRequest request, MyResponse response);
}
