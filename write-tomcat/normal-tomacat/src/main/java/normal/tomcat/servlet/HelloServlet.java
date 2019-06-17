package normal.tomcat.servlet;

import normal.tomcat.http.MyRequest;
import normal.tomcat.http.MyResponse;

public class HelloServlet extends AbstractServlet {


    @Override
    public void doGet(MyRequest request, MyResponse response) {
        doPost(request,response);
    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {
        response.write("你好呀");
    }
}
