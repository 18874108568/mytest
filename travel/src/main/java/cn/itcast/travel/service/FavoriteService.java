package cn.itcast.travel.service;

import cn.itcast.travel.domain.PageBean;

public interface FavoriteService {
    Boolean findFavorite(int rid, int uid);

    void addFavorite(String rid, int uid);

    PageBean addAllFavorite(int uid, int currentPage, int pageSize);
}
