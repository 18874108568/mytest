package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取URI /day/user/add
        String uri = req.getRequestURI();
        //截取最后的方法名 add
        String methed = uri.substring(uri.lastIndexOf("/") + 1);
        //反射来执行方法
        try {
            Method med = this.getClass().getDeclaredMethod(methed, HttpServletRequest.class, HttpServletResponse.class);
            med.setAccessible(true);
            med.invoke(this,req,resp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void writeValueAsString(Object obj,HttpServletResponse response) throws Exception {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper om=new ObjectMapper();
        String s = om.writeValueAsString(obj);
        response.getWriter().write(s);
    }

}
