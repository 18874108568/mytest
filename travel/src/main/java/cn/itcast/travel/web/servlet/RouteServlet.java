package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RountServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService rs = new RountServiceImpl();
    private  FavoriteService fs = new FavoriteServiceImpl();

    /**
     * 分页查询
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void pageQuery(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        request.setCharacterEncoding("utf-8");
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        String cid_str = request.getParameter("cid");
        String rname = request.getParameter("rname");

        int currentPage = 0;
        if (currentPage_str != null && currentPage_str.length() > 0) {
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSize_str != null && pageSize_str.length() > 0) {
            pageSize = Integer.parseInt(pageSize_str);
        } else {
            pageSize = 5;
        }
        int cid = 0;
        if (cid_str != null && cid_str.length() > 0 && !cid_str.equals("null")) {
            cid = Integer.parseInt(cid_str);
        }
        PageBean b = rs.pageQuery(currentPage, pageSize, cid, rname);
        writeValueAsString(b, response);
    }

    /**
     * 查询某一个旅游路劲的所有信息
     *
     * @param request
     * @param response
     * @throws Exception
     * @throws IOException
     */
    protected void findOne(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        request.setCharacterEncoding("utf-8");
        String rid = request.getParameter("rid");
        Route r = rs.findOne(rid);
        writeValueAsString(r, response);
    }

    /**
     * 查询某一条信息是否被收藏过
     *
     * @param request
     * @param response
     * @throws Exception
     * @throws IOException
     */
    protected void findmyFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        request.setCharacterEncoding("utf-8");
        int rid = Integer.parseInt(request.getParameter("rid"));
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user != null) {
            uid = user.getUid();
        }
        Boolean b = fs.findFavorite(rid, uid);
        ResultInfo r = new ResultInfo();
        r.setFlag(b);
        writeValueAsString(r, response);
    }

    protected void addFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        request.setCharacterEncoding("utf-8");
        User user = (User) request.getSession().getAttribute("user");
        String rid = request.getParameter("rid");
        if(user!=null){
            int uid = user.getUid();
        fs.addFavorite(rid,uid);
        }
    }
    protected void findAllFavorite(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        request.setCharacterEncoding("utf-8");
        String currentPage_str = request.getParameter("currentPage");
        String pageSize_str = request.getParameter("pageSize");
        int currentPage = 0;
        if (currentPage_str != null && currentPage_str.length() > 0) {
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSize_str != null && pageSize_str.length() > 0) {
            pageSize = Integer.parseInt(pageSize_str);
        } else {
            pageSize = 8;
        }
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        PageBean b=fs.addAllFavorite(user.getUid(),currentPage,pageSize);
        writeValueAsString(b,response);
    }
    public void favoriterank(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        String currentPage_str = request.getParameter("currentPage");//当前页码
        String pageSize_str = request.getParameter("pageSize");//每页显示条数
        String mname = request.getParameter("mname");//查询字段
        String dmo_str = request.getParameter("dmo");//最低价格
        String gmo_str = request.getParameter("gmo");//最高价格
        String cid = request.getParameter("cid");
        int currentPage = 0;
        if (currentPage_str != null && currentPage_str.length() > 0) {
            currentPage = Integer.parseInt(currentPage_str);
        } else {
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSize_str != null && pageSize_str.length() > 0) {
            pageSize = Integer.parseInt(pageSize_str);
        } else {
            pageSize = 8;
        }
        PageBean b=rs.favoriterank(currentPage,pageSize,mname,dmo_str,gmo_str,cid);
        writeValueAsString(b,response);
    }
    public void zhuti(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        List<Route> list=rs.zhuti();
        writeValueAsString(list,response);
    }
}
