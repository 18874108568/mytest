package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.FavoriteService;

import java.util.List;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao dao = new FavoriteDaoImpl();
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public Boolean findFavorite(int rid, int uid) {
        Favorite f = dao.findByRU(rid, uid);
        if (f == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void addFavorite(String rid, int uid) {
        dao.addFavorite(Integer.parseInt(rid), uid);
    }

    @Override
    public PageBean addAllFavorite(int uid, int currentPage, int pageSize) {
        PageBean p = new PageBean();
        p.setCurrentPage(currentPage);
        p.setPageSize(pageSize);
        //查询总记录数
        int totalConut = dao.queryConut(uid);
        p.setTotalConut(totalConut);
        //总页数
        int totalPage = totalConut % pageSize == 0 ? totalConut / pageSize : totalConut / pageSize + 1;
        p.setTotalPage(totalPage);
        //每页展示的数据集合
        //获取开始索引
        int start = (currentPage - 1) * pageSize;
        //查询所有的Rid路线
        List<Route> list = routeDao.finduidAll(uid, start, pageSize);
        p.setList(list);
        return p;
    }
}
