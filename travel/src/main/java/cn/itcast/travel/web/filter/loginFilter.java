package cn.itcast.travel.web.filter;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/myfavorite.html")
public class loginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        if(uri.contains("/css/") || uri.contains("/error/") || uri.contains("/fonts/") || uri.contains("/images/") || uri.contains("/img/") || uri.contains("/js/")) {
            chain.doFilter(req, resp);
        }else {
            User user = (User) request.getSession().getAttribute("user");
            if(user==null){
                //没登录
                /*ObjectMapper om=new ObjectMapper();
                ResultInfo r=new ResultInfo();
                r.setErrorMsg("请先登录");
                String s = om.writeValueAsString(r);
                response.getWriter().write(s);*/
                 response.sendRedirect(request.getContextPath()+"/login.html");
            }else {
                //已经登录
                chain.doFilter(req, resp);
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
