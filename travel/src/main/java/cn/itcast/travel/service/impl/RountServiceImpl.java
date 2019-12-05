package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImgDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImgDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RountServiceImpl implements RouteService{
    private RouteDao dao=new RouteDaoImpl();
    private RouteImgDao imgDao=new RouteImgDaoImpl();
    private SellerDao sellerDao=new SellerDaoImpl();
    private FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean pageQuery(int currentPage, int pageSize, int cid, String rname) {
        //封装PageBean
        PageBean p=new PageBean();
        p.setCurrentPage(currentPage);
        p.setPageSize(pageSize);
        //查询总记录数
        int totalConut=dao.queryConut(cid,rname);
        p.setTotalConut(totalConut);
        //总页数
        int totalPage=totalConut%pageSize==0?totalConut/pageSize:totalConut/pageSize+1;
        p.setTotalPage(totalPage);
        //每页展示的数据集合
        //获取开始索引
        int start=(currentPage-1)*pageSize;
        List<Route> list=dao.findByPage(cid,start,pageSize,rname);
        p.setList(list);
        return p;
    }

    @Override
    public Route findOne(String rid) {
        //先查寻单个旅游的信息
       Route r=dao.findOne(rid);
       //获取RouteImg所有信息
        List<RouteImg> list=imgDao.findImg(rid);
        r.setRouteImgList(list);
        //通过sid查询卖家信息
        int sid = r.getSid();
        Seller s=sellerDao.findSeller(sid);
        r.setSeller(s);
        //查询收藏次数
        int count=favoriteDao.findcount(r.getRid());
        //修改数据库的roote的收藏次数
        dao.updata(r.getRid(),count);
        r.setCount(count);
        return r;
    }

    @Override
    public PageBean favoriterank(int currentPage, int pageSize, String mname, String dmo, String gmo, String cid) {
        //封装PageBean
        PageBean p=new PageBean();
        p.setCurrentPage(currentPage);
        p.setPageSize(pageSize);
        //查询总记录数
        int favoriteConut=dao.favoriteConut(mname,dmo,gmo);
        p.setTotalConut(favoriteConut);
        //总页数
        int totalPage=favoriteConut%pageSize==0?favoriteConut/pageSize:favoriteConut/pageSize+1;
        p.setTotalPage(totalPage);
        //每页展示的数据集合
        //获取开始索引
        int start=(currentPage-1)*pageSize;
        List<Route> list=dao.rankByPage(start,pageSize,mname,dmo,gmo,cid);
        p.setList(list);
        return p;
    }

    @Override
    public List<Route> zhuti() {
      return dao.zhuti();
    }
}
