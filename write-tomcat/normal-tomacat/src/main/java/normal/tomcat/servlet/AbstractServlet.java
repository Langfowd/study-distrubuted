package normal.tomcat.servlet;

import normal.tomcat.http.MyRequest;
import normal.tomcat.http.MyResponse;
import normal.tomcat.servlet.MyServlet;

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
