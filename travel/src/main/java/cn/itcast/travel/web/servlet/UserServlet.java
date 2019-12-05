package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Random;

/**
 * 用户查询
 */
@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService us = new UserServiceImpl();
    //注册
    protected void regist(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        User luser = new User();
        try {
            BeanUtils.populate(luser, map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        ResultInfo r=new ResultInfo();
        //判断验证码是否正确
        if (checkcode_server != null && check.equalsIgnoreCase(checkcode_server)) {
            //验证成功，继续判断用户名可用
            //UserService us = new UserServiceImpl();
            Boolean b = us.regist(luser);
            if(b){
                //注册成功
                r.setFlag(true);
            }else {
                //注册失败
                r.setFlag(false);
                r.setErrorMsg("注册失败");
            }
        } else {
            //验证码验证失败
            r.setFlag(false);
            r.setErrorMsg("验证码错误");
        }
      /*   response.setContentType("application/json;charset=utf-8");
       ObjectMapper om=new ObjectMapper();
        String s = om.writeValueAsString(r);
        response.getWriter().write(s);*/
      writeValueAsString(r,response);
    }
    //激活邮箱
    protected void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String code = request.getParameter("code");
        //UserService us = new UserServiceImpl();
        Boolean b = us.active(code);
        String msg=null;
        if(b){
            //激活成功
            msg="激活成功，请<a href='http://47.112.213.44/travel/login.html'>登录</a>";
        }else {
            //激活失败
            msg="激活失败，请联系管理员：007";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }

    //登录
    protected void login(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        //获取参数
        request.setCharacterEncoding("utf-8");
        Map<String, String[]> map = request.getParameterMap();
        User log_user=new User();
        try {
            BeanUtils.populate(log_user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        String check = map.get("check")[0];
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");
        ResultInfo r=new ResultInfo();
        if(check!=null && checkcode_server!=null && checkcode_server.equalsIgnoreCase(check)){
            //验证码通过，判断用户是否存在
           // UserService us=new UserServiceImpl();
            User user= us.login(log_user);
            if(user==null){
                //用户账号或密码错误
                r.setFlag(false);
                r.setErrorMsg("账号或密码错误");
            }
            if(user!=null && !"Y".equals(user.getStatus())){
                //用户没有激活
                r.setFlag(false);
                r.setErrorMsg("邮箱没有激活，请先激活邮箱");
            }
            if(user!=null && "Y".equals(user.getStatus())){
                session.setAttribute("user",user);
                //登录成功
                r.setFlag(true);
            }
        }else {
            //验证码错误
            r.setFlag(false);
            r.setErrorMsg("验证码错误");
        }
      /*  ObjectMapper om=new ObjectMapper();
        String s = om.writeValueAsString(r);
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(s);*/
      writeValueAsString(r,response);
    }
    //登录后前端显示用户信息
    protected void findUser(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        response.setContentType("application/json;charset=utf-8");
        User user = (User) request.getSession().getAttribute("user");
        ResultInfo r=new ResultInfo();
        if(user==null){
            //没有登录
            r.setFlag(false);
            r.setErrorMsg("欢迎游客");
        }else {
            r.setFlag(true);
            r.setErrorMsg("欢迎回来!"+user.getName());
        }
       /* ObjectMapper om=new ObjectMapper();
        String s = om.writeValueAsString(user);
        response.getWriter().write(s);*/
       writeValueAsString(r,response);
    }


    //退出
    protected void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //跳转登录页面
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    //验证码
    public void checkCode(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        //服务器通知浏览器不要缓存
        response.setHeader("pragma","no-cache");
        response.setHeader("cache-control","no-cache");
        response.setHeader("expires","0");

        //在内存中创建一个长80，宽30的图片，默认黑色背景
        //参数一：长
        //参数二：宽
        //参数三：颜色
        int width = 80;
        int height = 30;
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);

        //获取画笔
        Graphics g = image.getGraphics();
        //设置画笔颜色为灰色
        g.setColor(Color.GRAY);
        //填充图片
        g.fillRect(0,0, width,height);

        //产生4个随机验证码，12Ey
        String checkCode = getCheckCode();
        //将验证码放入HttpSession中
        request.getSession().setAttribute("CHECKCODE_SERVER",checkCode);

        //设置画笔颜色为黄色
        g.setColor(Color.YELLOW);
        //设置字体的小大
        g.setFont(new Font("黑体",Font.BOLD,24));
        //向图片上写入验证码
        g.drawString(checkCode,15,25);

        //将内存中的图片输出到浏览器
        //参数一：图片对象
        //参数二：图片的格式，如PNG,JPG,GIF
        //参数三：图片输出到哪里去
        ImageIO.write(image,"PNG",response.getOutputStream());
    }
    /**
     * 产生4位随机字符串
     */
    private String getCheckCode() {
        String base = "0123456789ABCDEFGabcdefg";
        int size = base.length();
        Random r = new Random();
        StringBuffer sb = new StringBuffer();
        for(int i=1;i<=4;i++){
            //产生0到size-1的随机值
            int index = r.nextInt(size);
            //在base字符串中获取下标为index的字符
            char c = base.charAt(index);
            //将c放入到StringBuffer中去
            sb.append(c);
        }
        return sb.toString();
    }
}
