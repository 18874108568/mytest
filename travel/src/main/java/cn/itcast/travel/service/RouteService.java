package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteService {
    PageBean pageQuery(int currentPage, int pageSize, int cid, String rname);

    Route findOne(String rid);

    PageBean favoriterank(int currentPage, int pageSize, String mname, String dmo, String gmo, String cid);

    List<Route> zhuti();
}
