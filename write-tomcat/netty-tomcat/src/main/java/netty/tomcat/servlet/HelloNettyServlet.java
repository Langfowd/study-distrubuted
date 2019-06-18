package netty.tomcat.servlet;

import netty.tomcat.http.MyRequest;
import netty.tomcat.http.MyResponse;

public class HelloNettyServlet extends AbstractServlet {


    @Override
    public void doGet(MyRequest request, MyResponse response) {
        doPost(request,response);
    }

    @Override
    public void doPost(MyRequest request, MyResponse response) {
        response.write("你好呀,我是netty连接");
    }
}
