package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
    int queryConut(int cid, String rname);

    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    Route findOne(String rid);


    List<Route> finduidAll(int uid, int start, int pageSize);

    void updata(int rid, int count);

    int favoriteConut(String mname, String dmo, String gmo);

    List<Route> rankByPage(int start, int pageSize, String mname, String dmo, String gmo, String cid);

    List<Route> zhuti();
}
