package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService cs=new CategoryServiceImpl();
    /**
     * 查询所有数据
     * @param request
     * @param response
     * @throws Exception
     * @throws IOException
     */
    protected void findAll(HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
        //调用Serviec方法得到查询的数据
        List<Category> list = cs.findAll();
        //转为jsion数据
        writeValueAsString(list,response);
    }
}
